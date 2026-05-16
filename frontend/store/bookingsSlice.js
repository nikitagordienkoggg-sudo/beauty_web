import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { bookingApi } from '../api/bookingApi';

export const fetchBookings = createAsyncThunk('bookings/fetch', async (clientId) => {
  const { data } = await bookingApi.getClientBookings(clientId);
  return data;
});

const bookingsSlice = createSlice({
  name: 'bookings',
  initialState: { items: [], loading: false },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchBookings.pending, (state) => {
        state.loading = true;
      })
      .addCase(fetchBookings.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(fetchBookings.rejected, (state) => {
        state.loading = false;
      });
  }
});

export default bookingsSlice.reducer;
