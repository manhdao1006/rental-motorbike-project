/* eslint-disable @typescript-eslint/no-explicit-any */
interface ValidationResult {
    isValid: boolean
    message?: string
}

export const validateChuSo = (chuSo: string): ValidationResult => {
    if (!/^\d+$/.test(chuSo)) {
        return { isValid: false }
    }
    return { isValid: true }
}

export const validateSoDienThoai = (phone: string): ValidationResult => {
    if (!/^\d+$/.test(phone)) {
        return { isValid: false, message: 'Số điện thoại chỉ được chứa chữ số!' }
    }
    if (!/^\d{10}$/.test(phone)) {
        return { isValid: false, message: 'Số điện thoại phải có đúng 10 chữ số!' }
    }
    return { isValid: true }
}

export const validateSoCCCD = (cccd: string): ValidationResult => {
    if (!/^\d+$/.test(cccd)) {
        return { isValid: false, message: 'Số căn cước công dân chỉ được chứa chữ số!' }
    }
    if (!/^\d{12}$/.test(cccd)) {
        return { isValid: false, message: 'Số căn cước công dân phải có đúng 12 chữ số!' }
    }
    return { isValid: true }
}

export const validateSoGPLX = (gplx: string): ValidationResult => {
    if (!/^\d+$/.test(gplx)) {
        return { isValid: false, message: 'Số giấy phép lái xe chỉ được chứa chữ số!' }
    }
    if (!/^\d{12}$/.test(gplx)) {
        return { isValid: false, message: 'Số giấy phép lái xe phải có đúng 12 chữ số!' }
    }
    return { isValid: true }
}

export const validateEmail = (email: string): ValidationResult => {
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
    if (!emailRegex.test(email)) {
        return { isValid: false, message: 'Email không đúng định dạng!' }
    }
    return { isValid: true }
}
