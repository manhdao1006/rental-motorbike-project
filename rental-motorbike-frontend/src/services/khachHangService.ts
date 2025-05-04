import { API_ENDPOINTS } from '@/configs/apiConfig'
import axios from 'axios'
import { getToken } from './localStorageService'

export const getKhachHangs = async () => {
    const response = await axios.get(API_ENDPOINTS.KHACHHANG.GET_KHACHHANGS, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const getKhachHangByMaNguoiDung = async (maNguoiDung: string) => {
    const response = await axios.get(API_ENDPOINTS.KHACHHANG.GET_BY_MANGUOIDUNG(maNguoiDung), {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const getKhachHangByMaKhachHang = async (maNguoiDung: string) => {
    const response = await axios.get(API_ENDPOINTS.KHACHHANG.GET_BY_MAKHACHHANG(maNguoiDung), {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const addKhachHang = async (formData: FormData) => {
    try {
        const response = await axios.post(API_ENDPOINTS.KHACHHANG.ADD, formData, {
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

export const updateKhachHangByAdmin = async (maKhachHang: string, formData: FormData) => {
    try {
        const response = await axios.put(
            API_ENDPOINTS.KHACHHANG.UPDATE_BY_ADMIN(maKhachHang),
            formData,
            {
                headers: {
                    'Content-Type': 'multipart/form-data',
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

export const updateKhachHang = async (maKhachHang: string, formData: FormData) => {
    try {
        const response = await axios.put(API_ENDPOINTS.KHACHHANG.UPDATE(maKhachHang), formData, {
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

export const deleteKhachHang = async (maKhachHang: string) => {
    try {
        const response = await axios.put(
            API_ENDPOINTS.KHACHHANG.DELETE(maKhachHang),
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

export const banKhachHang = async (maKhachHang: string) => {
    try {
        const response = await axios.put(
            API_ENDPOINTS.KHACHHANG.BAN(maKhachHang),
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
