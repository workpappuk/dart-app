import { Image } from 'expo-image';
import { Platform, StyleSheet, Text } from 'react-native';
import { AppHeader } from '../components/core/AppHeader';


export default function HomeScreen() {  return (
  <>
  <AppHeader title="Reddit" showBack={false} />
  </>
  );
}