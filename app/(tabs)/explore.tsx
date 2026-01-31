
import React, { useEffect, useState } from 'react';
import { ActivityIndicator, FlatList, Text, View } from 'react-native';
import { Surface } from 'react-native-paper';
import { AppHeader } from '../components/core/AppHeader';
import { getUsers } from '../utils/services';



export default function ExploreScreen() {
   const [users, setUsers] = useState<any[]>([]);
   const [loading, setLoading] = useState(true);
   const [error, setError] = useState<string | null>(null);

   useEffect(() => {
      getUsers()
         .then(data => setUsers(data))
         .catch(() => setError('Failed to load users'))
         .finally(() => setLoading(false));
   }, []);

   return (
      <>
         <Surface style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            {loading ? (
               <ActivityIndicator size="large" />
            ) : error ? (
               <Text>{error}</Text>
            ) : (
               <FlatList
                  data={users}
                  keyExtractor={item => item.id?.toString() || Math.random().toString()}
                  renderItem={({ item }) => (
                     <View style={{ padding: 12 }}>
                        <Text style={{ fontSize: 16 }}>{item.name}</Text>
                     </View>
                  )}
               />
            )}
         </Surface>
      </>
   );
}