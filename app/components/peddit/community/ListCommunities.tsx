
import React from 'react';
import { View, FlatList, TextInput as RNTextInput } from 'react-native';
import { Card, Text, ActivityIndicator, useTheme, Divider, Button, IconButton, TextInput } from 'react-native-paper';
import { useInfiniteQuery, InfiniteData } from '@tanstack/react-query';
import { CommunityResponse, DartApiResponse, PageResponse } from '@/app/utils/types';
import { getCommunities } from '@/app/utils/services';
import AppAlert from '../../core/AppAlert';
import { ViewType } from './Communities';
import moment from 'moment';
import { LoadDummy } from '@/app/pages/admin/dashboard';

export default function ListCommunities(
    { callback, onUpdatePress }: {
        callback: (view: ViewType) => void,
        onUpdatePress: (id: string) => void
    }
) {
    const theme = useTheme();
    const [alert, setAlert] = React.useState<{ visible: boolean; message: string; title?: string }>({ visible: false, message: '', title: '' });
    const [search, setSearch] = React.useState('');
    const [searchTerm, setSearchTerm] = React.useState('');

    const PAGE_SIZE = 5;
    const {
        data,
        isLoading,
        isError,
        error,
        fetchNextPage,
        hasNextPage,
        isFetchingNextPage,
        refetch,
        isFetching,
    } = useInfiniteQuery<DartApiResponse<PageResponse<CommunityResponse>>>(
        {
            queryKey: ['communities', searchTerm],
            initialPageParam: 0,
            queryFn: async ({ pageParam }) => {
                const page = typeof pageParam === 'number' ? pageParam : 0;
                return getCommunities(searchTerm, page, PAGE_SIZE);
            },
            getNextPageParam: (lastPage?: DartApiResponse<PageResponse<CommunityResponse>>) => {
                const pageData = lastPage?.data;
                if (!pageData) return undefined;
                // Try both 'page' and 'number' for current page, and 'totalPages' for total
                const currentPage = typeof (pageData as any).page === 'number' ? (pageData as any).page : (typeof (pageData as any).number === 'number' ? (pageData as any).number : 0);
                const totalPages = typeof (pageData as any).totalPages === 'number' ? (pageData as any).totalPages : 1;
                if (currentPage + 1 < totalPages) {
                    return currentPage + 1;
                }
                return undefined;
            },
        }
    );

    // Refetch when searchTerm changes
    React.useEffect(() => {
        refetch();
    }, [searchTerm, refetch]);

    // Show a full loader only for the very first load
    if (isLoading && !isFetchingNextPage) {
        return (
            <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
                <ActivityIndicator animating color={theme.colors.primary} size="large" />
            </View>
        );
    }

    // Flatten all pages' content into a single array
    const communities = data?.pages?.flatMap((page) => page.data?.content ?? []) || [];

    return (
        <View style={{ flex: 1 }}>
            <AppAlert
                visible={alert.visible}
                title={alert.title}
                message={alert.message}
                onDismiss={() => setAlert((a) => ({ ...a, visible: false }))}
            />
            <View style={{ flexDirection: 'row', alignItems: 'center', marginBottom: 8, paddingHorizontal: 8 }}>
                <TextInput
                    mode="outlined"
                    placeholder="Search communities..."
                    value={search}
                    onChangeText={setSearch}
                    style={{ flex: 1, marginRight: 8, height: 44 }}
                    returnKeyType="search"
                    onSubmitEditing={() => {
                        setSearchTerm(search);
                    }}
                    left={<TextInput.Icon icon="magnify" />}
                />
                <Button
                    mode="contained"
                    onPress={() => {
                        setSearchTerm(search);
                    }}
                    style={{ height: 44, justifyContent: 'center' }}
                    loading={isFetching}
                >
                    Search
                </Button>
            </View>
            <View style={{
                alignItems: 'center',
                display: 'flex', flexDirection:
                    'row', justifyContent:
                    'space-between',
                // marginBottom: 8, 
                paddingHorizontal: 16
            }}>
                <LoadDummy type="communities" />
                <Button mode="contained" onPress={() => callback('add')} style={{ marginBottom: 16, alignSelf: 'center' }}>
                    Add Community
                </Button>
            </View>
            <FlatList
                data={communities}
                keyExtractor={(item) => item.id.toString()}
                ItemSeparatorComponent={() => <View style={{ marginVertical: 4 }} />}
                renderItem={({ item }) => (
                    <Card style={{
                        borderRadius: 12,
                        backgroundColor: theme.colors.elevation.level1
                    }}>
                        <Card.Title title={item.name} titleStyle={{ color: theme.colors.primary }} />
                        <Card.Content>
                            <Text style={{ color: theme.colors.onSurface }}>{item.description}</Text>
                            <Text style={{ color: theme.colors.onSurfaceVariant, marginTop: 4, fontSize: 12 }}>
                                Updated by: {item.updatedByUserInfo?.username || ''} at {moment(item.updatedAt).format('LLL')}
                            </Text>
                            <Text style={{ color: theme.colors.onSurfaceVariant, marginTop: 4, fontSize: 12 }}>
                                Created by: {item.createdByUserInfo?.username || ''} at {moment(item.createdAt).format('LLL')}
                            </Text>

                        </Card.Content>
                        <Card.Actions style={{ flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center' }}>
                            <Button onPress={() => {
                                setAlert({ visible: true, title: item.name, message: `Community ID: ${item.id}\nDescription: ${item.description}\nCreated By: ${item.createdByUserInfo?.username || ''}\nUpdated By: ${item.updatedByUserInfo?.username || ''}` });
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
                contentContainerStyle={{ paddingBottom: 32, paddingHorizontal: 16 }}
                style={{ flex: 1 }}
                onEndReached={() => {
                    if (hasNextPage && !isFetchingNextPage) {
                        fetchNextPage();
                    }
                }}
                onEndReachedThreshold={0.5}
                ListFooterComponent={
                    isFetchingNextPage ? (
                        <View style={{ paddingVertical: 16 }}>
                            <ActivityIndicator animating color={theme.colors.primary} />
                        </View>
                    ) : null
                }
            />
        </View>
    );
}
