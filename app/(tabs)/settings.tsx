
import { Button, Divider, Surface, Text } from "react-native-paper";
import { AppHeader } from "../components/core/AppHeader";
import { ThemeSwitch } from "../components/ThemeSwitch";
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import { View } from "react-native";
import { useRouter } from "expo-router";


export default function SettingsScreen() {
  const { theme, session } = useSelector((state: RootState) => state);
  const router = useRouter();
  return (
    <>
      <AppHeader title="Settings" showBack={false} />
      <Surface style={{ padding: 16, margin: 16, borderRadius: 8 }}>
        <View style={{ flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between' }}>
          <Text>Switch to {theme.dark ? 'Light' : 'Dark'}</Text>
          <ThemeSwitch />
        </View>
        <Divider style={{ marginVertical: 16 }} />
        {session.token ? (
          <View style={{ marginTop: 16 }}>
            <Button mode="contained" onPress={() => { router.push('/pages/admin/dashboard'); }}>
              Admin Dashoard
            </Button>
          </View>
        ) : (
           <View style={{ marginTop: 16 }}>
            <Button mode="contained" onPress={() => { router.push('/pages/auth/login'); }}>
              Login
            </Button>
          </View>
        )}

      </Surface>
    </>
  );
}