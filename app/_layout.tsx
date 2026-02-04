
import { DarkTheme, DefaultTheme, ThemeProvider } from '@react-navigation/native';
import { Stack } from 'expo-router';
import { StatusBar } from 'expo-status-bar';
import { MD3DarkTheme, MD3LightTheme, PaperProvider } from 'react-native-paper';
import 'react-native-reanimated';
import { Provider, useDispatch, useSelector } from 'react-redux';
import { RootState, setToken, store } from './redux/store';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React, { useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { SESSION_TOKEN_KEY } from './utils/constants';
import { setUserSessionToken } from './utils/axios';
import { SafeAreaProvider } from "react-native-safe-area-context";

function InnerRootLayout() {
  const dark = useSelector((state: RootState) => state.theme.dark);
  const dispatch = useDispatch();
  useEffect(() => {
    const loadSession = async () => {
      const token = await AsyncStorage.getItem(SESSION_TOKEN_KEY);
      if (token) {
        setUserSessionToken(token);
        dispatch(setToken(token));
      }
    };
    loadSession();
  }, []);


  // Merge MD3 themes with React Navigation theme requirements
  const navigationTheme = dark
    ? {
      ...MD3DarkTheme,
      colors: {
        ...MD3DarkTheme.colors,

        primary: '#C7F000',        // Volt green
        onPrimary: '#0B0B0B',

        background: '#0B0B0B',
        surface: '#121212',
        surfaceVariant: '#1A1A1A',

        text: '#FFFFFF',
        onSurface: '#FFFFFF',
        onSurfaceVariant: '#B3B3B3',

        outline: '#2A2A2A',
        disabled: '#6F6F6F',

        error: '#FF4D4D',
        onError: '#0B0B0B',
      },
    }
    : {
      ...MD3LightTheme,
      colors: {
        ...MD3LightTheme.colors,

        primary: '#1A1A1A',        // Nike black
        onPrimary: '#FFFFFF',

        secondary: '#C7F000',      // Volt accent
        onSecondary: '#0B0B0B',

        background: '#FFFFFF',
        surface: '#F7F7F7',
        surfaceVariant: '#EEEEEE',

        text: '#0B0B0B',
        onSurface: '#0B0B0B',
        onSurfaceVariant: '#5A5A5A',

        outline: '#D0D0D0',
        disabled: '#A0A0A0',

        error: '#D32F2F',
        onError: '#FFFFFF',
      },
    };;

  return (
    <SafeAreaProvider>

      <PaperProvider theme={navigationTheme}>
        <ThemeProvider value={dark ? DarkTheme : DefaultTheme}>
          <Stack>
            <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
            <Stack.Screen name="modal" options={{ presentation: 'modal', title: 'Modal' }} />
            <Stack.Screen name="pages/admin/dashboard" options={{ title: 'Admin Dashboard' }} />
            <Stack.Screen name="pages/auth/login" options={{ title: 'Login' }} />
          </Stack>
          <StatusBar style="auto" />
        </ThemeProvider>
      </PaperProvider>
    </SafeAreaProvider>
  );
}


export default function RootLayout() {
  const queryClient = React.useRef(new QueryClient()).current;
  return (
    <Provider store={store}>
      <QueryClientProvider client={queryClient}>
        <InnerRootLayout />
      </QueryClientProvider>
    </Provider>
  );
}
