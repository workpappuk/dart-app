
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

function InnerRootLayout() {
  const dark = useSelector((state: RootState) => state.theme.dark);
  const dispatch = useDispatch();
  useEffect(() => {
    const loadSession = async () => {
      const token = await AsyncStorage.getItem(SESSION_TOKEN_KEY);
      if (token) {
        dispatch(setToken(token));
      }
    };
    loadSession();
  }, []);

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
          <Stack.Screen name="pages/admin/dashboard" options={{ title: 'Admin Dashboard' }} />
          <Stack.Screen name="pages/auth/login" options={{ title: 'Login' }} />
        
        </Stack>
        <StatusBar style="auto" />
      </ThemeProvider>
    </PaperProvider>
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
