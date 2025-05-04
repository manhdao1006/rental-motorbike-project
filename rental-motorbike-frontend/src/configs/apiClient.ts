import {
    getRefreshToken,
    removeRefreshToken,
    removeToken,
    setRefreshToken,
    setToken
} from '@/services/localStorageService'
import axios from 'axios'
import { API_ENDPOINTS, BASE_URL } from './apiConfig'

export const apiClient = axios.create({
    baseURL: BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
})

const refreshToken = async () => {
    const refreshToken = getRefreshToken()
    if (!refreshToken) return

    try {
        const response = await apiClient.post(
            API_ENDPOINTS.AUTH.REFRESH_TOKEN,
            {},
            {
                headers: { 'Refresh-Token': refreshToken }
            }
        )
        if (response.data.code === 200) {
            setToken(response.data.result.accessToken)
            setRefreshToken(response.data.result.refreshToken)
            return response.data.result.accessToken
        }
    } catch (error) {
        console.log('Unable to refresh token', error)
        removeToken()
        removeRefreshToken()
    }
}

apiClient.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config

        if (error.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true

            const newAccessToken = await refreshToken()
            if (!newAccessToken) return Promise.reject(error)

            originalRequest.headers['Authorization'] = `Bearer ${newAccessToken}`
            return apiClient(originalRequest)
        }
        return Promise.reject(error)
    }
)
