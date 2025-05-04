import { API_ENDPOINTS } from '@/configs/apiConfig'
import axios from 'axios'
import { getToken } from './localStorageService'

export const getChuCuaHangs = async () => {
    const response = await axios.get(API_ENDPOINTS.CHUCUAHANG.GET_CHUCUAHANGS, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const getChuCuaHangByMaNguoiDung = async (maNguoiDung: string) => {
    const response = await axios.get(API_ENDPOINTS.CHUCUAHANG.GET_BY_MANGUOIDUNG(maNguoiDung), {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const addChuCuaHang = async (formData: FormData) => {
    try {
        const response = await axios.post(API_ENDPOINTS.CHUCUAHANG.ADD, formData, {
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

export const updateChuCuaHang = async (maChuCuaHang: string, formData: FormData) => {
    try {
        const response = await axios.put(API_ENDPOINTS.CHUCUAHANG.UPDATE(maChuCuaHang), formData, {
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

export const deleteChuCuaHang = async (maChuCuaHang: string) => {
    try {
        const response = await axios.put(
            API_ENDPOINTS.CHUCUAHANG.DELETE(maChuCuaHang),
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

export const banChuCuaHang = async (maChuCuaHang: string) => {
    try {
        const response = await axios.put(
            API_ENDPOINTS.CHUCUAHANG.BAN(maChuCuaHang),
            {},
            {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            }
        )

        if (response.data.code === 200) {
            return { success: true, message: 'Cấm thành công' }
        } else {
            return { success: false, message: 'Cấm thất bại' }
        }
    } catch (error) {
        console.error('Error deleting user:', error)
        return { success: false, message: 'Có lỗi khi xóa' }
    }
}
