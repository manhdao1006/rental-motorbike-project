import { apiClient } from '@/configs/apiClient'
import { API_ENDPOINTS } from '@/configs/apiConfig'
import axios from 'axios'
import {
    getToken,
    removeRefreshToken,
    removeToken,
    setMaNguoiDung,
    setRefreshToken,
    setToken
} from './localStorageService'

export const uploadWord = async (formData: Record<string, unknown>) => {
    try {
        const response = await axios.post(API_ENDPOINTS.AUTH.UPLOAD_WORD, formData, {
            headers: {
                'Content-Type': 'application/json'
            }
        })
        return response.data
    } catch (error) {
        console.error('Lỗi gửi upload:', error)
    }
}

export const sendEmail = async (emailData: { email: string; noiDung: string; tieuDe: string }) => {
    try {
        await axios.post(API_ENDPOINTS.AUTH.SEND_EMAIL, emailData, {
            headers: {
                'Content-Type': 'application/json'
            }
        })
    } catch (error) {
        console.error('Lỗi gửi email:', error)
    }
}

export const dangKyChuCuaHang = async (formData: FormData) => {
    try {
        const response = await axios.post(API_ENDPOINTS.AUTH.DANGKY_CHUCUAHANG, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
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

export const getNguoiDungByMaNguoiDung = async (maNguoiDung: string) => {
    const response = await apiClient.get(API_ENDPOINTS.NGUOIDUNG.GET_BY_MANGUOIDUNG(maNguoiDung), {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const fetchNguoiDung = async () => {
    const response = await apiClient.get(API_ENDPOINTS.NGUOIDUNG.GET_BY_EMAIL, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })
    return response.data.result.maNguoiDung
}

export const dangNhap = async (email: string, matKhau: string) => {
    try {
        const response = await apiClient.post(API_ENDPOINTS.AUTH.DANGNHAP, {
            email,
            matKhau
        })

        if (response.data.code === 200) {
            setToken(response.data.result.accessToken)
            setRefreshToken(response.data.result.refreshToken)
            const maNguoiDung = await fetchNguoiDung()
            if (maNguoiDung) {
                setMaNguoiDung(maNguoiDung)
            }
            return { success: true, message: 'Đăng nhập thành công!' }
        } else {
            return { success: false, message: 'Tên đăng nhập hoặc mật khẩu không đúng!' }
        }
    } catch (error) {
        console.log(error)
        return { success: false, message: 'Tên đăng nhập hoặc mật khẩu không đúng!' }
    }
}

export const dangXuat = () => {
    removeToken()
    removeRefreshToken()
}

export const dangKy = async (email: string, matKhau: string, hoVaTen: string) => {
    const response = await apiClient.post(API_ENDPOINTS.AUTH.DANGKY, {
        email,
        matKhau,
        hoVaTen
    })

    if (response.data.code === 200) {
        return { success: true, message: 'Đăng ký thành công!' }
    } else {
        return { success: false, message: 'Tên đăng nhập hoặc email đã tồn tại!' }
    }
}

export const getQuanHuyens = async () => {
    const response = await axios.get(API_ENDPOINTS.AUTH.GET_QUANHUYENS)

    return response.data.result
}

export const getQuanHuyenByMaPhuongXa = async (maPhuongXa: string) => {
    const response = await axios.get(API_ENDPOINTS.AUTH.GET_QUANHUYEN_BY_MAPHUONGXA(maPhuongXa))

    return response.data.result
}

export const getPhuongXasByMaQuanHuyen = async (maQuanHuyen: string) => {
    const response = await axios.get(API_ENDPOINTS.AUTH.GET_PHUONGXAS_BY_MAQUANHUYEN(maQuanHuyen))

    return response.data.result
}

export const getPhuongXaByMaPhuongXa = async (maPhuongXa: string) => {
    const response = await axios.get(API_ENDPOINTS.AUTH.GET_PHUONGXA_BY_MAPHUONGXA(maPhuongXa))

    return response.data.result
}

export const getPhuongXas = async () => {
    const response = await axios.get(API_ENDPOINTS.AUTH.GET_PHUONGXAS)

    return response.data.result
}
