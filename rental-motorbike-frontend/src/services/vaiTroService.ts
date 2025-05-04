import { API_ENDPOINTS } from '@/configs/apiConfig'
import axios from 'axios'
import { getToken } from './localStorageService'

export const getVaiTros = async () => {
    const response = await axios.get(API_ENDPOINTS.VAITRO.GET_VAITROS, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const getVaiTroByMaVaiTro = async (maVaiTro: string) => {
    const response = await axios.get(API_ENDPOINTS.VAITRO.GET_BY_MAVAITRO(maVaiTro), {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const addVaiTro = async (formData: FormData) => {
    try {
        const response = await axios.post(API_ENDPOINTS.VAITRO.ADD, formData, {
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${getToken()}`
            }
        })

        if (response.data.code === 200) {
            return { success: true, message: 'Thêm mới thành công' }
        } else {
            return { success: false, message: 'Thêm mới thất bại' }
        }
    } catch (error) {
        console.error('Error adding user:', error)
        return { success: false, message: 'Có lỗi khi thêm mới' }
    }
}

export const updateVaiTro = async (maVaiTro: string, formData: FormData) => {
    try {
        const response = await axios.put(API_ENDPOINTS.VAITRO.UPDATE(maVaiTro), formData, {
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${getToken()}`
            }
        })

        if (response.data.code === 200) {
            return { success: true, message: response.data.result }
        } else {
            return { success: false, message: 'Email đã tồn tại!' }
        }
    } catch (error) {
        console.error('Error updating user:', error)
        return { success: false, message: 'Email đã tồn tại!' }
    }
}
