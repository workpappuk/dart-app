

import React, { useState } from 'react';
import { View } from 'react-native';
import { TextInput, Button, Text, Surface } from 'react-native-paper';
import { useMutation } from '@tanstack/react-query';
import { registerUser } from '@/app/utils/services';
import { useRouter } from 'expo-router';
import { useDispatch } from 'react-redux';

export default function Register() {
    const [state, setState] = useState({
        username: 'user1',
        password: 'user',
        error: '',
    });

    const router = useRouter();

    const registerMutation = useMutation({
        mutationFn: async ({ username, password }: { username: string; password: string }) => {
            if (!username || !password) {
                throw new Error('Please enter username and password.');
            }
            const response = await registerUser({ username, password });
            console.log('Login response:', response);
            if (!response.success) {
                throw new Error('Invalid credentials.');
            }

            return response.data; // Only return data
        },
        onError: (error: any) => {
            setState((prev) => ({ ...prev, error: error.message || 'Register failed.' }));
        },
        onSuccess: async (data) => {
            setState((prev) => ({ ...prev, error: '' }));
            router.push('/pages/auth/login'); // Navigate to home on success
        },
    });

    const handleRegister = () => {
        setState((prev) => ({ ...prev, error: '' }));
        registerMutation.mutate({ username: state.username, password: state.password });
    };

    return (
        <View style={{ flex: 1, justifyContent: 'center', padding: 24 }}>
            <Surface style={{ padding: 24, borderRadius: 12, elevation: 2 }}>
                <Text variant="headlineMedium" style={{ marginBottom: 16, textAlign: 'center' }}>
                    Register
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
                    onPress={handleRegister}
                    loading={registerMutation.status === 'pending'}
                    disabled={registerMutation.status === 'pending'}
                >
                    Register
                </Button>
                <Button
                    mode="text"
                    onPress={() => router.push('/pages/auth/login')}
                    style={{ marginTop: 12 }}
                >
                    Login Here
                </Button>
            </Surface>
        </View>
    );
}