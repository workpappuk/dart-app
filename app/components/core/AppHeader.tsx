

import { useRouter } from 'expo-router';
import React, { ReactNode } from 'react';
import { StyleSheet, View } from 'react-native';
import { Appbar, useTheme } from 'react-native-paper';


interface AppHeaderProps {
  title?: string;
  showBack?: boolean;
  children?: ReactNode;
}

export const AppHeader: React.FC<AppHeaderProps> = ({ title = '', showBack = true, children }) => {
  const router = useRouter();
  const { colors } = useTheme();

  return (
    <Appbar.Header
      style={[styles.header, { backgroundColor: colors.background, shadowColor: colors.primary }]} 
    >
      {showBack && <Appbar.BackAction onPress={() => router.back()} color={colors.primary} />}
      <View 
        style={[styles.titleContainer, { backgroundColor: colors.background }]}
      >
        <Appbar.Content
          title={title}
          titleStyle={[styles.title, { color: colors.onBackground }]}
        />
      </View>
      {children}
    </Appbar.Header>
  );
};

const styles = StyleSheet.create({
  header: {
    elevation: 4,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 2,
  },
  titleContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    fontWeight: 'bold',
    fontSize: 18,
    textAlign: 'center',
  },
});
