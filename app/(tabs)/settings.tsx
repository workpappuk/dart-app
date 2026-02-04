
import { Button, Divider, Surface, Switch, Text, useTheme } from "react-native-paper";
import { AppHeader } from "../components/core/AppHeader";
import { ThemeSwitch } from "../components/ThemeSwitch";
import { useDispatch, useSelector } from 'react-redux';
import { RootState, setToken, toggleFeatureFlag } from '../redux/store';
import { SafeAreaViewBase, View } from "react-native";
import { useRouter } from "expo-router";
import { logoutUser } from "../utils/services";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { clearUserSessionToken } from "../utils/axios";
import useAuth from "../utils/hooks/useAuth";
import { SafeAreaView } from "react-native-safe-area-context";
import { FeatureFlags } from "../utils/constants";

export default function SettingsScreen() {
  const dark = useSelector((state: RootState) => state.theme.dark);
  const {colors} = useTheme();
  const dispatch = useDispatch();

  const session = useSelector((state: RootState) => state.session);
  const router = useRouter();
  const { revokeSession } = useAuth();
  return (
    <SafeAreaView style={{ flex: 1 }}>

      <AppHeader title="Settings" showBack={false} />

      <Surface style={{ padding: 16, margin: 16, borderRadius: 8, backgroundColor: colors.background}}>
        <View style={{ flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between' }}>
          <Text>Switch to {dark ? 'Light' : 'Dark'}</Text>
          <ThemeSwitch />
        </View>

        <FeatureFlag />

        <Divider style={{ marginVertical: 16 }} />
        {session.token ? (
          <>
            <View style={{ marginTop: 16 ,backgroundColor: colors.background }}>
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
    </SafeAreaView>
  );
}

export function FeatureFlag() {
  const dispatch = useDispatch();
  const { featureFlags } = useSelector((state: RootState) => state.session);

  return (
    <>
      {Object.entries(featureFlags).map(([key, value]) => (
        <>
          <Divider style={{ marginTop: 16 }} />
          <View key={key} style={{ flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between', marginTop: 16 }}>
            <Text style={{ textTransform: 'capitalize' }}>{key.replace(/_/g, ' ').toLocaleLowerCase()}</Text>
            <Switch
              value={value}
              onValueChange={(value: boolean) => { dispatch(toggleFeatureFlag({ key, value })); }}
              style={{ marginRight: 8 }}
              accessibilityLabel="Toggle theme"
            />
          </View>
        </>
      ))}
    </>
  );
}