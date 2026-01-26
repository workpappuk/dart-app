
import React, { useState } from 'react';
import { View } from 'react-native';
import { TextInput, Button, Surface, Text } from 'react-native-paper';
import { useMutation } from '@tanstack/react-query';
import { CommunityRequest } from '@/app/utils/types';
import { createCommunity } from '@/app/utils/services';

export function AddCommunity() {
  const [form, setForm] = useState<CommunityRequest>({ name: '', description: '' });
  const [error, setError] = useState('');

  const mutation = useMutation({
    mutationFn: async (data: CommunityRequest) => {
      if (!data.name.trim()) throw new Error('Name is required');
      return await createCommunity(data);
    },
    onError: (err: any) => setError(err.message || 'Failed to add community'),
    onSuccess: () => {
      setForm({ name: '', description: '' });
      setError('');
      // Optionally show a success message or navigate
    },
  });

  const handleChange = (key: keyof CommunityRequest, value: string) => {
    setForm((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = () => {
    setError('');
    mutation.mutate(form);
  };

  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center', padding: 24 }}>
      <Surface style={{ width: '100%', maxWidth: 400, padding: 24, borderRadius: 16, elevation: 4 }}>
        <Text variant="headlineMedium" style={{ marginBottom: 20, textAlign: 'center' }}>
          Create Community
        </Text>
        <TextInput
          label="Community Name"
          value={form.name}
          onChangeText={(text) => handleChange('name', text)}
          style={{ marginBottom: 16 }}
          mode="outlined"
        />
        <TextInput
          label="Description"
          value={form.description}
          onChangeText={(text) => handleChange('description', text)}
          style={{ marginBottom: 16 }}
          mode="outlined"
          multiline
          numberOfLines={3}
        />
        {error ? <Text style={{ color: 'red', marginBottom: 12 }}>{error}</Text> : null}
        <Button
          mode="contained"
          onPress={handleSubmit}
          loading={mutation.isPending}
          disabled={mutation.isPending}
          style={{ borderRadius: 8 }}
        >
          Add Community
        </Button>
      </Surface>
    </View>
  );
}