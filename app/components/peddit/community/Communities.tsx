
import { AddCommunity } from "./AddCommunity";
import { View } from 'react-native';
import ListCommunities from "./ListCommunities";
import React from "react";
import { Button } from "react-native-paper";
import { UpdateCommunity } from "./UpdateCommunity";

export type ViewType = 'list' | 'add' | 'update';

export function Communities() {
    const [view, setView] = React.useState<ViewType>('list');
    const [selectedCommunityId, setSelectedCommunityId] = React.useState<string | null>(null);

    const changeView = (newView: ViewType) => {
        setView(newView);
    }

    const onUpdatePress = (id: string) => {
        setSelectedCommunityId(id);
        setView('update');
    }

    return (
        <View style={{ flex: 1, paddingVertical: 8 }}>
            {view === 'update' &&
                <UpdateCommunity communityId={selectedCommunityId} callback={changeView} />
            }
            {view === 'add' && <AddCommunity callback={changeView} />}
            {view === 'list' &&
                <ListCommunities callback={changeView} onUpdatePress={onUpdatePress} />
            }
        </View>
    );
}