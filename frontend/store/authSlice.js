import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { bookingApi } from '../api/bookingApi';

export const login = createAsyncThunk('auth/login', async (credentials) => {
  const { data } = await bookingApi.login(credentials);
  localStorage.setItem('token', data.token);
  return data;
});

const authSlice = createSlice({
  name: 'auth',
  initialState: { token: localStorage.getItem('token'), loading: false, error: null },
  reducers: {
    logout(state) {
      localStorage.removeItem('token');
      state.token = null;
    }
  },
  extraReducers: (builder) => {
    builder
      .addCase(login.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(login.fulfilled, (state, action) => {
        state.loading = false;
        state.token = action.payload.token;
      })
      .addCase(login.rejected, (state) => {
        state.loading = false;
        state.error = 'Login failed';
      });
  }
});

export const { logout } = authSlice.actions;
export default authSlice.reducer;
