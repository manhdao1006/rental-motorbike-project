import { API_ENDPOINTS } from '@/configs/apiConfig'
import axios from 'axios'
import { getToken } from './localStorageService'

export const getKhieuNais = async (maChuCuaHang: string) => {
    const response = await axios.get(
        API_ENDPOINTS.KHIEUNAI.GET_KHIEUNAIS_BY_CHUCUAHANG(maChuCuaHang),
        {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        }
    )

    return response.data.result
}
