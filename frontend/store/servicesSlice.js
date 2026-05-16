import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { bookingApi } from '../api/bookingApi';

export const fetchServices = createAsyncThunk('services/fetch', async (name) => {
  const { data } = await bookingApi.getServices(name);
  return data;
});

const servicesSlice = createSlice({
  name: 'services',
  initialState: { items: [], loading: false },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchServices.pending, (state) => {
        state.loading = true;
      })
      .addCase(fetchServices.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(fetchServices.rejected, (state) => {
        state.loading = false;
      });
  }
});

export default servicesSlice.reducer;
