import { API_ENDPOINTS } from '@/configs/apiConfig'
import axios from 'axios'
import { getToken } from './localStorageService'

export const getNguoiDungsByMaVaiTro = async (maVaiTro: string) => {
    const response = await axios.get(API_ENDPOINTS.NGUOIDUNG.GET_NGUOIDUNGS_BY_MAVAITRO(maVaiTro), {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data.result
}
