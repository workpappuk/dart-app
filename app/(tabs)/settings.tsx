import { Surface, Text } from "react-native-paper";
import { AppHeader } from "../components/core/AppHeader";
import { ThemeSwitch } from "../components/ThemeSwitch";

export default function SettingsScreen() {  return (
  <>
  <AppHeader title="Settings" showBack={false} />
  <Surface style={{ padding: 16, margin: 16, borderRadius: 8 }}>
    <Text>This is the Settings screen.</Text>
    <ThemeSwitch />
  </Surface>
  </>
  );
}