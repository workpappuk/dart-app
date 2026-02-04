

import React, { useState } from 'react';
import { View } from 'react-native';
import { TextInput, Button, Text, Surface } from 'react-native-paper';
import { useMutation } from '@tanstack/react-query';
import { loginUser } from '@/app/utils/services';
import { setToken } from '@/app/redux/store';
import { useRouter } from 'expo-router';
import { useDispatch } from 'react-redux';

import { set } from 'lodash';
import useAuth from '@/app/utils/hooks/useAuth';

export default function Login() {
    const [state, setState] = useState({
        username: 'user1',
        password: 'user',
        error: '',
    });

    const router = useRouter();
    const dispatch = useDispatch();
    const { intiateSession, revokeSession, } = useAuth();

    const loginMutation = useMutation({
        mutationFn: async ({ username, password }: { username: string; password: string }) => {
            if (!username || !password) {
                throw new Error('Please enter username and password.');
            }
            const response = await loginUser({ username, password });
            console.log('Login response:', response);
            if (!response.success) {
                throw new Error('Invalid credentials.');
            }

            return response.data; // Only return data
        },
        onError: (error: any) => {
            setState((prev) => ({ ...prev, error: error.message || 'Login failed.' }));
        },
        onSuccess: async (data) => {
            const { token } = data;
            if (token) {
                await intiateSession(token);
            } else {
               await revokeSession();
            }

            setState((prev) => ({ ...prev, error: '' }));
           router.push('/'); // Navigate to home on success
        },
    });

    const handleLogin = () => {
        setState((prev) => ({ ...prev, error: '' }));
        loginMutation.mutate({ username: state.username, password: state.password });
    };

    return (
        <View style={{ flex: 1, justifyContent: 'center', padding: 24 }}>
            <Surface style={{ padding: 24, borderRadius: 12, elevation: 2 }}>
                <Text variant="headlineMedium" style={{ marginBottom: 16, textAlign: 'center' }}>
                    Login
                </Text>
                <TextInput
                    label="username or email"
                    value={state.username}
                    onChangeText={(text) => setState((prev) => ({ ...prev, username: text }))}
                    autoCapitalize="none"
                    keyboardType="email-address"
                    style={{ marginBottom: 12 }}
                />
                <TextInput
                    label="Password"
                    value={state.password}
                    onChangeText={(text) => setState((prev) => ({ ...prev, password: text }))}
                    secureTextEntry
                    style={{ marginBottom: 12 }}
                />
                {state.error ? (
                    <Text style={{ color: 'red', marginBottom: 12 }}>{state.error}</Text>
                ) : null}
                <Button
                    mode="contained"
                    onPress={handleLogin}
                    loading={loginMutation.status === 'pending'}
                    disabled={loginMutation.status === 'pending'}
                >
                    Login
                </Button>
                <Button
                    mode="text"
                    onPress={() => router.push('/pages/auth/register')}
                    style={{ marginTop: 12 }}
                >
                    Register Here
                </Button>
            </Surface>
        </View>
    );
}