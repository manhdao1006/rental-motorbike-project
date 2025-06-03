export const BASE_URL = 'http://localhost:8080'

export const API_ENDPOINTS = {
    PAYMENT: {
        CREATE_PAYMENT: `${BASE_URL}/api/auth/payment/create-payment`,
        REFUND: `${BASE_URL}/api/auth/payment/refund`
    },
    AUTH: {
        DANGKY_QUANTRIVIEN: `${BASE_URL}/api/auth/register-admin`,
        DANGKY_CHUCUAHANG: `${BASE_URL}/api/auth/dangKyChuCuaHang`,
        DANGKY: `${BASE_URL}/api/auth/register`,
        DANGNHAP: `${BASE_URL}/api/auth/login`,
        DANGXUAT: `${BASE_URL}/api/auth/logout`,
        REFRESH_TOKEN: `${BASE_URL}/api/auth/refresh-token`,
        SEND_EMAIL: `${BASE_URL}/api/auth/send-email`,
        UPLOAD_WORD: `${BASE_URL}/api/auth/upload-word`,
        GET_QUANHUYENS: `${BASE_URL}/api/auth/quan-huyen`,
        GET_QUANHUYEN_BY_MAPHUONGXA: (maPhuongXa: string) =>
            `${BASE_URL}/api/auth/quan-huyen/maPhuongXa/${maPhuongXa}`,
        GET_PHUONGXAS_BY_MAQUANHUYEN: (maQuanHuyen: string) =>
            `${BASE_URL}/api/auth/phuong-xa/maQuanHuyen/${maQuanHuyen}`,
        GET_PHUONGXAS: `${BASE_URL}/api/auth/phuong-xa`,
        GET_PHUONGXA_BY_MAPHUONGXA: (maPhuongXa: string) =>
            `${BASE_URL}/api/auth/phuong-xa/maPhuongXa/${maPhuongXa}`
    },
    NGUOIDUNG: {
        GET_BY_MANGUOIDUNG: (maNguoiDung: string) => `${BASE_URL}/api/auth/view/${maNguoiDung}`,
        GET_BY_EMAIL: `${BASE_URL}/api/auth/profile`,
        GET_NGUOIDUNGS_BY_MAVAITRO: (maVaiTro: string) =>
            `${BASE_URL}/quan-tri-vien/nguoi-dung/role/${maVaiTro}`
    },
    VAITRO: {
        GET_VAITROS: `${BASE_URL}/quan-tri-vien/vai-tro/list`,
        GET_BY_MAVAITRO: (maVaiTro: string) =>
            `${BASE_URL}/quan-tri-vien/vai-tro/maVaiTro/${maVaiTro}`,
        ADD: `${BASE_URL}/quan-tri-vien/vai-tro/add`,
        UPDATE: (maVaiTro: string) => `${BASE_URL}/quan-tri-vien/vai-tro/edit/${maVaiTro}`
    },
    KHACHHANG: {
        GET_KHACHHANGS: `${BASE_URL}/chu-cua-hang/khach-hang/list`,
        GET_BY_MANGUOIDUNG: (maNguoiDung: string) =>
            `${BASE_URL}/chu-cua-hang/khach-hang/${maNguoiDung}`,
        GET_BY_MAKHACHHANG: (maNguoiDung: string) =>
            `${BASE_URL}/khach-hang/khach-hang/${maNguoiDung}`,
        ADD: `${BASE_URL}/chu-cua-hang/khach-hang/add`,
        UPDATE: (maNguoiDung: string) => `${BASE_URL}/khach-hang/khach-hang/edit/${maNguoiDung}`,
        UPDATE_BY_ADMIN: (maNguoiDung: string) =>
            `${BASE_URL}/chu-cua-hang/khach-hang/edit/${maNguoiDung}`,
        DELETE: (maNguoiDung: string) =>
            `${BASE_URL}/chu-cua-hang/khach-hang/delete/${maNguoiDung}`,
        BAN: (maNguoiDung: string) => `${BASE_URL}/chu-cua-hang/khach-hang/ban/${maNguoiDung}`
    },
    CHUCUAHANG: {
        GET_CHUCUAHANGS: `${BASE_URL}/chu-cua-hang/chu-cua-hang/list`,
        GET_BY_MANGUOIDUNG: (maNguoiDung: string) =>
            `${BASE_URL}/chu-cua-hang/chu-cua-hang/${maNguoiDung}`,
        GET_BY_MAXEMAY: (maXeMay: string) =>
            `${BASE_URL}/chu-cua-hang/chu-cua-hang/xe-may/${maXeMay}`,
        ADD: `${BASE_URL}/chu-cua-hang/chu-cua-hang/add`,
        UPDATE: (maNguoiDung: string) =>
            `${BASE_URL}/chu-cua-hang/chu-cua-hang/edit/${maNguoiDung}`,
        DELETE: (maNguoiDung: string) =>
            `${BASE_URL}/chu-cua-hang/chu-cua-hang/delete/${maNguoiDung}`,
        BAN: (maNguoiDung: string) => `${BASE_URL}/chu-cua-hang/chu-cua-hang/ban/${maNguoiDung}`
    },
    NHANVIEN: {
        GET_NHANVIENS: `${BASE_URL}/chu-cua-hang/nhan-vien/list`,
        GET_NHANVIENS_BY_CHUCUAHANG: (maNguoiDung: string) =>
            `${BASE_URL}/chu-cua-hang/nhan-vien/maChuCuaHang/${maNguoiDung}`,
        GET_BY_MANGUOIDUNG: (maNguoiDung: string) =>
            `${BASE_URL}/chu-cua-hang/nhan-vien/maNhanVien/${maNguoiDung}`,
        ADD: `${BASE_URL}/chu-cua-hang/nhan-vien/add`,
        UPDATE: (maNguoiDung: string) => `${BASE_URL}/chu-cua-hang/nhan-vien/edit/${maNguoiDung}`,
        DELETE: (maNguoiDung: string) => `${BASE_URL}/chu-cua-hang/nhan-vien/delete/${maNguoiDung}`,
        BAN: (maNguoiDung: string) => `${BASE_URL}/chu-cua-hang/nhan-vien/ban/${maNguoiDung}`
    },
    LOAIKHIEUNAI: {
        GET_LOAIKHIEUNAIS: `${BASE_URL}/api/auth/loai-khieu-nai/list`,
        GET_BY_MALOAIKHIEUNAI: (maLoaiKhieuNai: string) =>
            `${BASE_URL}/api/auth/maLoaiKhieuNai/${maLoaiKhieuNai}`,
        ADD: `${BASE_URL}/quan-tri-vien/loai-khieu-nai/add`,
        UPDATE: (maLoaiKhieuNai: string) =>
            `${BASE_URL}/quan-tri-vien/loai-khieu-nai/edit/${maLoaiKhieuNai}`,
        DELETE: (maLoaiKhieuNai: string) =>
            `${BASE_URL}/quan-tri-vien/loai-khieu-nai/delete/${maLoaiKhieuNai}`
    },
    DANHMUCXE: {
        GET_DANHMUCXES: `${BASE_URL}/api/auth/danh-muc-xe/list`,
        GET_BY_MADANHMUCXE: (maDanhMucXe: string) =>
            `${BASE_URL}/api/auth/maDanhMucXe/${maDanhMucXe}`,
        ADD: `${BASE_URL}/quan-tri-vien/danh-muc-xe/add`,
        UPDATE: (maDanhMucXe: string) =>
            `${BASE_URL}/quan-tri-vien/danh-muc-xe/edit/${maDanhMucXe}`,
        DELETE: (maDanhMucXe: string) =>
            `${BASE_URL}/quan-tri-vien/danh-muc-xe/delete/${maDanhMucXe}`
    },
    XEMAY: {
        GET_XEMAYS_BY_CHUCUAHANG: (maChuCuaHang: string) =>
            `${BASE_URL}/chu-cua-hang/xe-may/chu-cua-hang/${maChuCuaHang}`,
        GET_BY_MAXEMAY_BY_CHUCUAHANG: (maXeMay: string) =>
            `${BASE_URL}/chu-cua-hang/xe-may/maXeMay/${maXeMay}`,
        ADD_BY_CHUCUAHANG: `${BASE_URL}/chu-cua-hang/xe-may/add`,
        UPDATE_BY_CHUCUAHANG: (maXeMay: string) =>
            `${BASE_URL}/chu-cua-hang/xe-may/edit/${maXeMay}`,
        DELETE_BY_CHUCUAHANG: (maXeMay: string) =>
            `${BASE_URL}/chu-cua-hang/xe-may/delete/${maXeMay}`,

        GET_QUAN_HAICHAUS: `${BASE_URL}/api/trang-chu/list-xe-hai-chau`,
        GET_QUAN_THANHKHES: `${BASE_URL}/api/trang-chu/list-xe-thanh-khe`,
        GET_QUAN_SONTRAS: `${BASE_URL}/api/trang-chu/list-xe-son-tra`,
        GET_QUAN_NGUHANHSONS: `${BASE_URL}/api/trang-chu/list-xe-ngu-hanh-son`,
        GET_QUAN_LIENCHIEUS: `${BASE_URL}/api/trang-chu/list-xe-lien-chieu`,

        GET_BY_MAQUANHUYEN: (maQuanHuyen: string) =>
            `${BASE_URL}/api/trang-chu/list-xe/${maQuanHuyen}`,
        GET_BY_MAXEMAY: (maXeMay: string) => `${BASE_URL}/api/trang-chu/maXeMay/${maXeMay}`,

        GET_XEMAYS: `${BASE_URL}/khach-hang/xe-may/list`
    },
    DONHANG: {
        GET_DONHANGS_BY_CHUCUAHANG: (maChuCuaHang: string, trangThaiDonHang: string) =>
            `${BASE_URL}/chu-cua-hang/don-hang/maChuCuaHang/${maChuCuaHang}/${trangThaiDonHang}`,
        GET_DONHANGS_BY_NHANVIEN: (maNhanVien: string, trangThaiDonHang: string) =>
            `${BASE_URL}/nhan-vien/don-hang/maNhanVien/${maNhanVien}/${trangThaiDonHang}`,
        GET_DONHANGS_BY_KHACHHANG: (maKhachHang: string) =>
            `${BASE_URL}/khach-hang/don-hang/maKhachHang/${maKhachHang}`,
        GET_BY_MADONHANG: (maDonHang: string) => `${BASE_URL}/api/auth/maDonHang/${maDonHang}`,
        ADD: `${BASE_URL}/khach-hang/don-hang/add`,
        UPDATE_BY_CHUCUAHANG: (maDonHang: string) =>
            `${BASE_URL}/chu-cua-hang/don-hang/edit/${maDonHang}`
    },
    CHITIETDONHANG: {
        GET_CHITIETDONHANGS_BY_CHUCUAHANG: (maChuCuaHang: string, trangThaiDonHang: string) =>
            `${BASE_URL}/chu-cua-hang/chi-tiet-don-hang/maChuCuaHang/${maChuCuaHang}/${trangThaiDonHang}`,
        GET_CHITIETDONHANGS_BY_NHANVIEN: (maNhanVien: string, trangThaiDonHang: string) =>
            `${BASE_URL}/nhan-vien/chi-tiet-don-hang/maNhanVien/${maNhanVien}/${trangThaiDonHang}`,
        ADD_BY_CHUCUAHANG: `${BASE_URL}/chu-cua-hang/chi-tiet-don-hang/add`,
        UPDATE_BY_CHUCUAHANG: (maChiTietDonHang: string) =>
            `${BASE_URL}/chu-cua-hang/chi-tiet-don-hang/edit/${maChiTietDonHang}`,

        GET_ONE_BY_MADONHANG_AND_MAXEMAY: (maDonHang: string, maXeMay: string) =>
            `${BASE_URL}/nhan-vien/chi-tiet-don-hang/chiTietDonHang/${maDonHang}/${maXeMay}`,
        UPDATE_BY_NHANVIEN: (maDonHang: string, maXeMay: string) =>
            `${BASE_URL}/nhan-vien/chi-tiet-don-hang/edit/${maDonHang}/${maXeMay}`,

        GET_CHITIETDONHANGS_BY_KHACHHANG: (maKhachHang: string, trangThaiDonHang: string) =>
            `${BASE_URL}/khach-hang/chi-tiet-don-hang/maKhachHang/${maKhachHang}/${trangThaiDonHang}`,
        GET_CHITIETDONHANGS_BY_TRANGTHAIDONHANG_KHACHHANG: (
            maNguoiDung: string,
            trangThaiDonHang: string
        ) => `${BASE_URL}/khach-hang/chi-tiet-don-hang/list/${maNguoiDung}/${trangThaiDonHang}`,
        ADD_BY_KHACHHANG: `${BASE_URL}/khach-hang/chi-tiet-don-hang/add`,
        UPDATE_BY_KHACHHANG: (maChiTietDonHang: string) =>
            `${BASE_URL}/khach-hang/chi-tiet-don-hang/edit/${maChiTietDonHang}`
    },
    KHIEUNAI: {
        GET_KHIEUNAIS_BY_CHUCUAHANG: (maNguoiDung: string) =>
            `${BASE_URL}/chu-cua-hang/khieu-nai/list/${maNguoiDung}`,
        GET_KHIEUNAIS_BY_KHACHHANG: (maNguoiDung: string) =>
            `${BASE_URL}/khach-hang/khieu-nai/list/${maNguoiDung}`,
        GET_BY_MAKHIEUNAI: (maKhieuNai: string) =>
            `${BASE_URL}/khach-hang/khieu-nai/maKhieuNai/${maKhieuNai}`,
        ADD: `${BASE_URL}/khach-hang/khieu-nai/add`,
        UPDATE: (maKhieuNai: string) => `${BASE_URL}/chu-cua-hang/khieu-nai/edit/${maKhieuNai}`,
        DELETE: (maKhieuNai: string) => `${BASE_URL}/khach-hang/khieu-nai/delete/${maKhieuNai}`
    }
}
