export const useTime = (dateTime: string) => {
    const date = new Date(dateTime)
    return date.toLocaleString('vi-VN', {
        hour: '2-digit',
        minute: '2-digit'
    })
}
