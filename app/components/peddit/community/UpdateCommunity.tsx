


import React, { useEffect } from 'react';
import { View } from 'react-native';
import { TextInput, Button, Text, useTheme } from 'react-native-paper';
import { useMutation } from '@tanstack/react-query';
import { useForm, Controller } from 'react-hook-form';
import { CommunityRequest } from '@/app/utils/types';
import { updateCommunity, fetchCommunityDetails } from '@/app/utils/services';
import AppAlert from '../../core/AppAlert';
import { ViewType } from './Communities';


export function UpdateCommunity(
    { communityId, callback }: {
        communityId: string | null;
        callback: (view: ViewType) => void
    }
) {
    const theme = useTheme();

    const { control, handleSubmit, reset, formState: { errors } } = useForm<CommunityRequest>({
        defaultValues: { name: '', description: '' },
    });
    const [error, setError] = React.useState('');
    const [alert, setAlert] = React.useState<{ visible: boolean; message?: string; title?: string; isError?: boolean }>({ visible: false, message: '', title: '', isError: false });

    const mutation = useMutation({
        mutationFn: async (data: CommunityRequest) => {
            return await updateCommunity(communityId, data);
        },
        onError: (err: any) => {
            setError(err.message || 'Failed to update community');
            setAlert({ visible: true, message: err.message || 'Failed to update community', title: 'Error', isError: true });
        },
        onSuccess: () => {

            setAlert({ visible: true, title: 'Community updated successfully!', isError: false });
        },
    });

    const onSubmit = (data: CommunityRequest) => {
        setError('');
        mutation.mutate(data);
    };


    useEffect(() => {
        // Fetch community details by ID and populate the form
        if (communityId) {

            fetchCommunityDetails(communityId).then((data) => {
                console.log('Fetched community data:', data);
                const { name, description } = data.data;
                reset({
                    name: name || '',
                    description: description || '',
                });
            });
        }
    }, [communityId]);
    return (
        <View>
            <AppAlert
                visible={alert.visible}
                title={alert.title}
                message={alert.message!}
                onDismiss={() => {
                    setAlert((a) => ({ ...a, visible: false }))
                    reset();
                    setError('');
                    callback('list');

                }}
                confirmText="OK"
            // Optionally, you can style based on alert.isError
            />
            <Text variant="headlineMedium" style={{ marginBottom: 20, textAlign: 'center' }}>
                Update Community
            </Text>
            <Controller
                control={control}
                name="name"
                rules={{ required: 'Community name is required' }}
                render={({ field: { onChange, onBlur, value } }) => (
                    <TextInput
                        label="Community Name"
                        value={value}
                        onChangeText={onChange}
                        onBlur={onBlur}
                        style={{ marginBottom: 4 }}
                        mode="outlined"
                        error={!!errors.name}
                    />
                )}
            />
            {errors.name && <Text style={{ color: theme.colors.error, marginBottom: 12 }}>{errors.name.message}</Text>}
            <Controller
                control={control}
                name="description"
                rules={{ required: 'Description is required' }}
                render={({ field: { onChange, onBlur, value } }) => (
                    <TextInput
                        label="Description"
                        value={value}
                        onChangeText={onChange}
                        onBlur={onBlur}
                        style={{ marginBottom: 4 }}
                        mode="outlined"
                        multiline
                        numberOfLines={3}
                        error={!!errors.description}
                    />
                )}
            />
            {errors.description && <Text style={{ color: theme.colors.error, marginBottom: 12 }}>{errors.description.message}</Text>}
            {error ? <Text style={{ color: theme.colors.error, marginBottom: 12 }}>{error}</Text> : null}
            <Button
                mode="contained"
                onPress={handleSubmit(onSubmit)}
                loading={mutation.isPending}
                disabled={mutation.isPending}
                style={{ borderRadius: 8 }}
            >
                Update Community
            </Button>

            <Button
                mode="text"
                onPress={() => callback('list')}
                style={{ marginTop: 12 }}
            >
                Back to Communities
            </Button>
        </View>
    );
}