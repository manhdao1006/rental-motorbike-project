import { API_ENDPOINTS } from '@/configs/apiConfig'
import axios from 'axios'
import { getToken } from '../localStorageService'

export const getDanhMucXes = async () => {
    const response = await axios.get(API_ENDPOINTS.DANHMUCXE.GET_DANHMUCXES, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const getDanhMucXeByMaDanhMucXe = async (maDanhMucXe: string) => {
    const response = await axios.get(API_ENDPOINTS.DANHMUCXE.GET_BY_MADANHMUCXE(maDanhMucXe), {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}

export const addDanhMucXe = async (formData: FormData) => {
    try {
        const response = await axios.post(API_ENDPOINTS.DANHMUCXE.ADD, formData, {
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

export const updateDanhMucXe = async (maDanhMucXe: string, formData: FormData) => {
    try {
        const response = await axios.put(API_ENDPOINTS.DANHMUCXE.UPDATE(maDanhMucXe), formData, {
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

export const deleteDanhMucXe = async (maDanhMucXe: string) => {
    try {
        const response = await axios.put(
            API_ENDPOINTS.DANHMUCXE.DELETE(maDanhMucXe),
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
