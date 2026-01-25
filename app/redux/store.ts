
import { configureStore, createSlice, PayloadAction } from '@reduxjs/toolkit';


interface SessionState {
  token: string | null;
  isSessionValid: boolean;
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
  },
});


export const { setToken } = sessionSlice.actions;
export const { toggleTheme, setDark } = themeSlice.actions;

export const store = configureStore({
  reducer: {
    theme: themeSlice.reducer,
    session: sessionSlice.reducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
