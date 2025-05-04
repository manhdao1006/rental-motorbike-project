import { API_ENDPOINTS } from '@/configs/apiConfig'
import axios from 'axios'
import { getToken } from './localStorageService'

export const getLoaiKhieuNais = async () => {
    const response = await axios.get(API_ENDPOINTS.LOAIKHIEUNAI.GET_LOAIKHIEUNAIS, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const getLoaiKhieuNaiByMaLoaiKhieuNai = async (maLoaiKhieuNai: string) => {
    const response = await axios.get(
        API_ENDPOINTS.LOAIKHIEUNAI.GET_BY_MALOAIKHIEUNAI(maLoaiKhieuNai),
        {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        }
    )

    return response.data.result
}

export const addLoaiKhieuNai = async (formData: FormData) => {
    try {
        const response = await axios.post(API_ENDPOINTS.LOAIKHIEUNAI.ADD, formData, {
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

export const updateLoaiKhieuNai = async (maLoaiKhieuNai: string, formData: FormData) => {
    try {
        const response = await axios.put(
            API_ENDPOINTS.LOAIKHIEUNAI.UPDATE(maLoaiKhieuNai),
            formData,
            {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${getToken()}`
                }
            }
        )

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

export const deleteLoaiKhieuNai = async (maLoaiKhieuNai: string) => {
    try {
        const response = await axios.put(
            API_ENDPOINTS.LOAIKHIEUNAI.DELETE(maLoaiKhieuNai),
            {},
            {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            }
        )

        if (response.data.code === 200) {
            return { success: true, message: 'Xóa thành công' }
        } else {
            return { success: false, message: 'Xóa thất bại' }
        }
    } catch (error) {
        console.error('Error deleting user:', error)
        return { success: false, message: 'Có lỗi khi xóa' }
    }
}
