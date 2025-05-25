import { API_ENDPOINTS } from '@/configs/apiConfig'
import axios from 'axios'
import { getToken } from './localStorageService'

export const getKhieuNaisByChuCuaHang = async (maChuCuaHang: string) => {
    const response = await axios.get(
        API_ENDPOINTS.KHIEUNAI.GET_KHIEUNAIS_BY_CHUCUAHANG(maChuCuaHang),
        {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        }
    )

    return response.data.result
}

export const getKhieuNaisByKhachHang = async (maKhachHang: string) => {
    const response = await axios.get(
        API_ENDPOINTS.KHIEUNAI.GET_KHIEUNAIS_BY_KHACHHANG(maKhachHang),
        {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        }
    )

    return response.data.result
}

export const getKhieuNaiByMaKhieuNai = async (maKhieuNai: string) => {
    const response = await axios.get(API_ENDPOINTS.KHIEUNAI.GET_BY_MAKHIEUNAI(maKhieuNai), {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const addKhieuNai = async (formData: FormData) => {
    try {
        const response = await axios.post(API_ENDPOINTS.KHIEUNAI.ADD, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
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

export const updateKhieuNai = async (maKhieuNai: string, formData: FormData) => {
    try {
        const response = await axios.put(API_ENDPOINTS.KHIEUNAI.UPDATE(maKhieuNai), formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
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
