
import React from 'react';
import { View, FlatList, ScrollView } from 'react-native';
import { Card, Text, ActivityIndicator, useTheme, Divider, Button, IconButton } from 'react-native-paper';
import { useQuery } from '@tanstack/react-query';
import { CommunityResponse, DartApiResponse, PageResponse } from '@/app/utils/types';
import { getCommunities } from '@/app/utils/services';
import AppAlert from '../../core/AppAlert';
import { ViewType } from './Communities';

export default function ListCommunities(
    { callback, onUpdatePress }: {
        callback: (view: ViewType) => void,
        onUpdatePress: (id: string) => void
    }
) {
    const theme = useTheme();
    const [alert, setAlert] = React.useState<{ visible: boolean; message: string; title?: string }>({ visible: false, message: '', title: '' });

    const { data, isLoading, isError, error } = useQuery<DartApiResponse<CommunityResponse[]>>({
        queryKey: ['communities'],
        queryFn: getCommunities,
    });

    if (isLoading) {
        return <ActivityIndicator animating color={theme.colors.primary} style={{ marginTop: 32 }} />;
    }

    return (
        <ScrollView style={{ flex: 1, padding: 16 }}>
            <AppAlert
                visible={alert.visible}
                title={alert.title}
                message={alert.message}
                onDismiss={() => setAlert((a) => ({ ...a, visible: false }))}
            />
            <View style={{ alignItems: 'center', display: 'flex', flexDirection: 'row', justifyContent: 'space-between', marginBottom: 16 }}>
                <Text variant="headlineSmall" style={{ marginBottom: 16, textAlign: 'center' }}>
                    Communities
                </Text>
                <Button mode="contained" onPress={() => callback('add')} style={{ marginBottom: 16, alignSelf: 'center' }}>
                    Add New Community
                </Button>
            </View>

            <FlatList
                data={data?.data || []}
                keyExtractor={(item) => item.id.toString()}
                ItemSeparatorComponent={() => <View style={{ marginVertical: 4 }} />}
                renderItem={({ item }) => (
                    <Card style={{ marginBottom: 8, borderRadius: 12, backgroundColor: theme.colors.elevation.level1 }}>
                        <Card.Title title={item.name} titleStyle={{ color: theme.colors.primary }} />
                        <Card.Content>
                            <Text style={{ color: theme.colors.onSurface }}>{item.description}</Text>
                            <Text style={{ color: theme.colors.onSurfaceVariant, marginTop: 4, fontSize: 12 }}>
                                Created by: {item.createdBy}
                            </Text>
                        </Card.Content>
                        <Card.Actions style={{ flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center' }}>
                            <Button onPress={() => {
                                setAlert({ visible: true, title: item.name, message: `Community ID: ${item.id}\nDescription: ${item.description}\nCreated By: ${item.createdBy}` });
                            }}>
                                View Details
                            </Button>
                            <View style={{ flexDirection: 'row', alignItems: 'center' }}>
                                <IconButton
                                    icon="pencil"
                                    size={22}
                                    onPress={() => {
                                        onUpdatePress(item.id);
                                    }}
                                    accessibilityLabel="Edit Community"
                                />
                                <IconButton
                                    icon="delete"
                                    size={22}
                                    iconColor={theme.colors.error}
                                    onPress={() => {
                                        setAlert({ visible: true, title: 'Delete Community', message: 'Delete functionality not implemented yet.' });
                                    }}
                                    accessibilityLabel="Delete Community"
                                />
                            </View>
                        </Card.Actions>
                    </Card>
                )}
                ListEmptyComponent={<Text style={{ textAlign: 'center', marginTop: 32 }}>No communities found.</Text>}
            />
        </ScrollView>
    );
}
