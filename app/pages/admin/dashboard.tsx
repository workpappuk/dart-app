import { AppHeader } from "@/app/components/core/AppHeader";
import { Surface, Text } from "react-native-paper";

export default function AdminDashboard() {
  return (
    <>
      <Surface style={{ padding: 16, margin: 16, borderRadius: 8 }}>
        <Text>Welcome to the Admin Dashboard!</Text>
        <CommunitiesSection />
      </Surface>
    </>
  );
}

function CommunitiesSection() {
  return (
    <Surface style={{ padding: 16, margin: 16, borderRadius: 8 }}>
      <Text>Communities Section</Text>
    </Surface>
  );
}