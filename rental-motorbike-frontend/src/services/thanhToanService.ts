import { apiClient } from '@/configs/apiClient'
import { API_ENDPOINTS } from '@/configs/apiConfig'
import { getToken } from './localStorageService'

export const createPayment = async (amount: number, bankCode: string, txnRef: string) => {
    const response = await apiClient.get(API_ENDPOINTS.PAYMENT.CREATE_PAYMENT, {
        params: {
            vnp_Amount: amount,
            vnp_BankCode: bankCode,
            vnp_TxnRef: txnRef
        },
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data
}

export const refundDeposit = async (maDonHang: string, soTienHoan: number) => {
    const response = await apiClient.post(API_ENDPOINTS.PAYMENT.REFUND, {
        params: {
            maDonHang,
            soTienHoan
        },
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })

    return response.data
}
