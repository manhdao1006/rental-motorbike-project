import axios from 'axios'
import { getToken } from './localStorageService'

export const startConversation = async (
    maKhachHang: string,
    maChuCuaHang: string
): Promise<string> => {
    const response = await axios.post('http://localhost:8080/chat/start', null, {
        params: { maKhachHang, maChuCuaHang },
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })
    return response.data
}

export const getHoiThoaisByKhachHang = async (maKhachHang: string): Promise<unknown[]> => {
    const res = await axios.get(`http://localhost:8080/chat/conversations/khach-hang`, {
        params: { maKhachHang },
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })
    return res.data
}

export const getHoiThoaisByChuCuaHang = async (maChuCuaHang: string): Promise<unknown[]> => {
    const res = await axios.get(`http://localhost:8080/chat/conversations/chu-cua-hang`, {
        params: { maChuCuaHang },
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })
    return res.data
}
