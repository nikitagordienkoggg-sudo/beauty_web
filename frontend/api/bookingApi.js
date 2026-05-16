import httpClient from './httpClient';

export const bookingApi = {
  getServices: (name) => httpClient.get('/services', { params: name ? { name } : {} }),
  createBooking: (payload) => httpClient.post('/bookings', payload),
  getClientBookings: (clientId) => httpClient.get(`/bookings/client/${clientId}`),
  cancelBooking: (bookingId) => httpClient.delete(`/bookings/${bookingId}`),
  login: (payload) => httpClient.post('/auth/login', payload),
  register: (payload) => httpClient.post('/auth/register', payload),
  pay: ({ bookingId, amount }) => httpClient.post('/payments', null, { params: { bookingId, amount } })
};
