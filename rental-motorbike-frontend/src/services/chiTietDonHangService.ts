import { API_ENDPOINTS } from '@/configs/apiConfig'
import axios from 'axios'
import { getToken } from './localStorageService'

export const getChiTietDonHangByMaDonHangAndMaXeMay = async (
    maDonHang: string,
    maXeMay: string
) => {
    const response = await axios.get(
        API_ENDPOINTS.CHITIETDONHANG.GET_ONE_BY_MADONHANG_AND_MAXEMAY(maDonHang, maXeMay),
        {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        }
    )

    return response.data.result
}

export const getChiTietDonHangsByMaKhachHang = async (
    maKhachHang: string,
    trangThaiDonHang: string
) => {
    const response = await axios.get(
        API_ENDPOINTS.CHITIETDONHANG.GET_CHITIETDONHANGS_BY_KHACHHANG(
            maKhachHang,
            trangThaiDonHang
        ),
        {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        }
    )

    return response.data.result
}

export const getChiTietDonHangsByMaNhanVien = async (
    maNhanVien: string,
    trangThaiDonHang: string
) => {
    const response = await axios.get(
        API_ENDPOINTS.CHITIETDONHANG.GET_CHITIETDONHANGS_BY_NHANVIEN(maNhanVien, trangThaiDonHang),
        {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        }
    )

    return response.data.result
}

export const getChiTietDonHangsByMaChuCuaHang = async (
    maChuCuaHang: string,
    trangThaiDonHang: string
) => {
    const response = await axios.get(
        API_ENDPOINTS.CHITIETDONHANG.GET_CHITIETDONHANGS_BY_CHUCUAHANG(
            maChuCuaHang,
            trangThaiDonHang
        ),
        {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        }
    )

    return response.data.result
}

export const addChiTietDonHang = async (formData: FormData) => {
    try {
        const response = await axios.post(API_ENDPOINTS.CHITIETDONHANG.ADD_BY_KHACHHANG, formData, {
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

export const updateChiTietDonHang = async (
    maDonHang: string,
    maXeMay: string,
    formData: FormData
) => {
    try {
        const response = await axios.put(
            API_ENDPOINTS.CHITIETDONHANG.UPDATE_BY_NHANVIEN(maDonHang, maXeMay),
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
