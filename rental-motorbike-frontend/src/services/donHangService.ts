import { API_ENDPOINTS } from '@/configs/apiConfig'
import axios from 'axios'
import { getToken } from './localStorageService'

export const getDonHangByMaDonHang = async (maDonHang: string) => {
    const response = await axios.get(API_ENDPOINTS.DONHANG.GET_BY_MADONHANG(maDonHang), {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const getDonHangsByChuCuaHang = async (maNguoiDung: string, trangThaiDonHang: string) => {
    const response = await axios.get(
        API_ENDPOINTS.DONHANG.GET_DONHANGS_BY_CHUCUAHANG(maNguoiDung, trangThaiDonHang),
        {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        }
    )

    return response.data.result
}

export const getDonHangsByNhanVien = async (maNguoiDung: string, trangThaiDonHang: string) => {
    const response = await axios.get(
        API_ENDPOINTS.DONHANG.GET_DONHANGS_BY_NHANVIEN(maNguoiDung, trangThaiDonHang),
        {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        }
    )

    return response.data.result
}

export const getDonHangsByKhachHang = async (maNguoiDung: string) => {
    const response = await axios.get(API_ENDPOINTS.DONHANG.GET_DONHANGS_BY_KHACHHANG(maNguoiDung), {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const addDonHang = async (formData: FormData) => {
    try {
        const response = await axios.post(API_ENDPOINTS.DONHANG.ADD, formData, {
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${getToken()}`
            }
        })

        if (response.data.code === 200) {
            return { success: true, message: 'Thêm mới thành công', result: response.data.result }
        } else {
            return { success: false, message: 'Thêm mới thất bại' }
        }
    } catch (error) {
        console.error('Error adding user:', error)
        return { success: false, message: 'Có lỗi khi thêm mới' }
    }
}

export const updateDonHang = async (maDonHang: string, formData: FormData) => {
    try {
        const response = await axios.put(
            API_ENDPOINTS.DONHANG.UPDATE_BY_CHUCUAHANG(maDonHang),
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
