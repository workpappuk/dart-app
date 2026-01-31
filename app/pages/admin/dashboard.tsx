import { RootState } from "@/app/redux/store";
import { useRouter } from "expo-router";
import { useState } from "react";
import { Divider, Surface, Text } from "react-native-paper";
import { useSelector } from "react-redux";
import { SafeAreaView, ScrollView, StyleSheet, View } from 'react-native';
import { SegmentedButtons } from 'react-native-paper';
import { Communities } from "@/app/components/peddit/community/Communities";
import { add } from "lodash";
import { DUMMY_COMMUNITIES } from "@/app/utils/constants";
import { createCommunity } from "@/app/utils/services";
export default function AdminDashboard() {

  const { session } = useSelector((state: RootState) => state);

  const router = useRouter();

  const [value, setValue] = useState('communities');

  const options = [
    { value: 'communities', label: 'Communities' },
    { value: 'posts', label: 'Posts' },
    { value: 'users', label: 'Users' },
  ];
  return (
    <View style={[styles.container, { flex: 1 }]}> 
      <SegmentedButtons
        value={value}
        onValueChange={setValue}
        buttons={options}
      />
      <View style={{ flex: 1, marginVertical: 8 }}>
        {value === 'communities' && <CommunitiesSection />}
        {value === 'posts' && <PostsSection />}
        {value === 'users' && <UsersSection />}
      </View>
    </View>
  );
}

function UsersSection() {
  return (
    <Surface style={{ padding: 16, margin: 16, borderRadius: 8 }}>
      <Text>Users Section</Text>
    </Surface>
  );
}

function PostsSection() {
  return (
    <Surface style={{ padding: 16, margin: 16, borderRadius: 8 }}>
      <Text>Posts Section</Text>
    </Surface>
  );
}

function CommunitiesSection() {
  return (
    <>
    {/* <LoadDummy type="communities" />  */}
    <Communities />    
    </>
  );
}

const styles = StyleSheet.create({
  container: {
    padding: 16,
  },
});

function addDummyCommunities() {
  DUMMY_COMMUNITIES.forEach(async (community) => {
    await createCommunity(community);
  });
}

function LoadDummy({ type }: { type: 'communities' | 'posts' | 'users' }) {
  const router = useRouter();

  const handleLoadDummy = () => {
    if (type === 'communities') {
      console.log('Loading dummy communities');
      addDummyCommunities();
    } else if (type === 'posts') {
      console.log('Loading dummy posts');
    } else if (type === 'users') {
      console.log('Loading dummy users');
    }
  };

  return (
    <View style={{ marginBottom: 16, alignItems: 'center' }}>
      <Text onPress={handleLoadDummy} style={{ color: 'blue' }}>
        Load Dummy {type.charAt(0).toUpperCase() + type.slice(1)}
      </Text>
    </View>
  );
}