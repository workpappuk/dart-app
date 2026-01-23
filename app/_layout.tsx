
import { DarkTheme, DefaultTheme, ThemeProvider } from '@react-navigation/native';
import { Stack } from 'expo-router';
import { StatusBar } from 'expo-status-bar';
import { MD3DarkTheme, MD3LightTheme, PaperProvider } from 'react-native-paper';
import 'react-native-reanimated';
import { Provider, useSelector } from 'react-redux';
import { RootState, store } from './redux/store';

function InnerRootLayout() {
  const dark = useSelector((state: RootState) => state.theme.dark);
  // Merge MD3 themes with React Navigation theme requirements
  const navigationTheme = dark
    ? {
        ...DarkTheme,
        colors: {
          ...DarkTheme.colors,
          ...MD3DarkTheme.colors,
        },
      }
    : {
        ...DefaultTheme,
        colors: {
          ...DefaultTheme.colors,
          ...MD3LightTheme.colors,
        },
      };

  return (
    <PaperProvider>
      <ThemeProvider value={navigationTheme}>
        <Stack>
          <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
          <Stack.Screen name="modal" options={{ presentation: 'modal', title: 'Modal' }} />
        </Stack>
        <StatusBar style="auto" />
      </ThemeProvider>
    </PaperProvider>
  );
}

export default function RootLayout() {
  return (
    <Provider store={store}>
      <InnerRootLayout />
    </Provider>
  );
}
