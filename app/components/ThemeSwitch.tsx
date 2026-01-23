import React from 'react';
import { Switch } from 'react-native-paper';
import { useDispatch, useSelector } from 'react-redux';
import { RootState, toggleTheme } from '../redux/store';

export const ThemeSwitch: React.FC = () => {
  const dark = useSelector((state: RootState) => state.theme.dark);
  const dispatch = useDispatch();

  return (
    <Switch
      value={dark}
      onValueChange={(value: boolean) => { dispatch(toggleTheme(value)); }}
      style={{ marginRight: 8 }}
      accessibilityLabel="Toggle theme"
    />
  );
};
