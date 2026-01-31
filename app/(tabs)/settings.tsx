
import { Button, Divider, Surface, Text } from "react-native-paper";
import { AppHeader } from "../components/core/AppHeader";
import { ThemeSwitch } from "../components/ThemeSwitch";
import { useDispatch, useSelector } from 'react-redux';
import { RootState, setToken } from '../redux/store';
import { View } from "react-native";
import { useRouter } from "expo-router";
import { logoutUser } from "../utils/services";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { clearUserSessionToken } from "../utils/axios";
import useAuth from "../utils/hooks/useAuth";


export default function SettingsScreen() {
  const dark = useSelector((state: RootState) => state.theme.dark);

  const session = useSelector((state: RootState) => state.session);
  const router = useRouter();
  const { revokeSession } = useAuth();
  return (
    <>
      <AppHeader title="Settings" showBack={false} />
      <Surface style={{ padding: 16, margin: 16, borderRadius: 8 }}>
        <View style={{ flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between' }}>
          <Text>Switch to {dark ? 'Light' : 'Dark'}</Text>
          <ThemeSwitch />
        </View>
        <Divider style={{ marginVertical: 16 }} />
        {session.token ? (
          <>
            <View style={{ marginTop: 16 }}>
              <Button mode="contained" onPress={() => { router.push('/pages/admin/dashboard'); }}>
                Admin Dashoard
              </Button>
            </View>
            <View style={{ marginTop: 16 }}>
              <Button mode="contained" onPress={async () => {
                logoutUser()
                  .catch((err) => {
                    console.error('Logout error:', err);
                  })
                  .finally(async () => {
                    await revokeSession();
                  });
              }}>
                Logout
              </Button>
            </View>
          </>
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