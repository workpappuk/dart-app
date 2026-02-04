
import { configureStore, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { FeatureFlags } from '../utils/constants';


interface SessionState {
  token: string | null;
  isSessionValid: boolean;
  featureFlags: { [key: string]: boolean };
}

interface ThemeState {
  dark: boolean;
}

const initialState: ThemeState = {
  dark: false,
};

const initialSessionState: SessionState = {
  token: null,
  isSessionValid: false,
  featureFlags: FeatureFlags,
};


const themeSlice = createSlice({
  name: 'theme',
  initialState,
  reducers: {
    toggleTheme: (state) => {
      state.dark = !state.dark;
    },
    setDark: (state, action: PayloadAction<boolean>) => {
      state.dark = action.payload;
    },
  },
});



const sessionSlice = createSlice({
  name: 'session',
  initialState: initialSessionState,
  reducers: {
    setToken: (state, action: PayloadAction<string | null>) => {
      state.token = action.payload;
      state.isSessionValid = action.payload !== null;
    },
    toggleFeatureFlag: (state, action: PayloadAction<{ key: string; value: boolean }>) => {
      const { key, value } = action.payload;
      state.featureFlags[key] = value;
    },
  },
});


export const { setToken, toggleFeatureFlag } = sessionSlice.actions;
export const { toggleTheme, setDark } = themeSlice.actions;

export const store = configureStore({
  reducer: {
    theme: themeSlice.reducer,
    session: sessionSlice.reducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
