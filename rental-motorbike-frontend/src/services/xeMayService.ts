import { API_ENDPOINTS } from '@/configs/apiConfig'
import axios from 'axios'
import { getToken } from './localStorageService'

export const getXeMaysByMaQuanHuyen = async (maQuanHuyen: string) => {
    const response = await axios.get(API_ENDPOINTS.XEMAY.GET_BY_MAQUANHUYEN(maQuanHuyen))

    return response.data.result
}

export const getXeMayByMaXeMay = async (maXeMay: string) => {
    const response = await axios.get(API_ENDPOINTS.XEMAY.GET_BY_MAXEMAY(maXeMay))

    return response.data.result
}

export const getXeMaysInQuanHaiChau = async () => {
    const response = await axios.get(API_ENDPOINTS.XEMAY.GET_QUAN_HAICHAUS)

    return response.data.result
}

export const getXeMaysInQuanThanhKhe = async () => {
    const response = await axios.get(API_ENDPOINTS.XEMAY.GET_QUAN_THANHKHES)

    return response.data.result
}

export const getXeMaysInQuanSonTra = async () => {
    const response = await axios.get(API_ENDPOINTS.XEMAY.GET_QUAN_SONTRAS)

    return response.data.result
}

export const getXeMaysInQuanNguHanhSon = async () => {
    const response = await axios.get(API_ENDPOINTS.XEMAY.GET_QUAN_NGUHANHSONS)

    return response.data.result
}

export const getXeMaysInQuanLienChieu = async () => {
    const response = await axios.get(API_ENDPOINTS.XEMAY.GET_QUAN_LIENCHIEUS)

    return response.data.result
}

export const getXeMaysByChuCuaHang = async (maChuCuaHang: string) => {
    const response = await axios.get(API_ENDPOINTS.XEMAY.GET_XEMAYS_BY_CHUCUAHANG(maChuCuaHang))

    return response.data.result
}

export const getXeMays = async () => {
    const response = await axios.get(API_ENDPOINTS.XEMAY.GET_XEMAYS)

    return response.data.result
}

export const getXeMayByMaXeMayByChuCuaHang = async (maXeMay: string) => {
    const response = await axios.get(API_ENDPOINTS.XEMAY.GET_BY_MAXEMAY_BY_CHUCUAHANG(maXeMay), {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const addXeMay = async (formData: FormData) => {
    try {
        const response = await axios.post(API_ENDPOINTS.XEMAY.ADD_BY_CHUCUAHANG, formData, {
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

export const updateXeMay = async (maXeMay: string, formData: FormData) => {
    try {
        const response = await axios.put(
            API_ENDPOINTS.XEMAY.UPDATE_BY_CHUCUAHANG(maXeMay),
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

export const deleteXeMay = async (maXeMay: string) => {
    try {
        const response = await axios.put(
            API_ENDPOINTS.XEMAY.DELETE_BY_CHUCUAHANG(maXeMay),
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
