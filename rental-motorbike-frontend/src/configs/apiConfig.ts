export const BASE_URL = 'http://localhost:8080'

export const API_ENDPOINTS = {
    AUTH: {
        DANGKY_QUANTRIVIEN: `${BASE_URL}/api/auth/register-admin`,
        DANGKY_NHANVIEN: `${BASE_URL}/api/auth/register-staff`,
        DANGKY_CHUCUAHANG: `${BASE_URL}/api/auth/register-lessor`,
        DANGKY: `${BASE_URL}/api/auth/register`,
        DANGNHAP: `${BASE_URL}/api/auth/login`,
        DANGXUAT: `${BASE_URL}/api/auth/logout`,
        REFRESH_TOKEN: `${BASE_URL}/api/auth/refresh-token`,
        GET_QUANHUYENS: `${BASE_URL}/api/auth/quan-huyen`,
        GET_QUANHUYEN_BY_MAPHUONGXA: (maPhuongXa: string) =>
            `${BASE_URL}/api/auth/quan-huyen/maPhuongXa=${maPhuongXa}`,
        GET_PHUONGXAS_BY_MAQUANHUYEN: (maQuanHuyen: string) =>
            `${BASE_URL}/api/auth/phuong-xa/maQuanHuyen=${maQuanHuyen}`,
        GET_PHUONGXAS: `${BASE_URL}/api/auth/phuong-xa`
    },
    NGUOIDUNG: {
        GET_BY_MANGUOIDUNG: (maNguoiDung: string) => `${BASE_URL}/api/auth/view/${maNguoiDung}`,
        GET_BY_EMAIL: `${BASE_URL}/api/auth/profile`,
        GET_NGUOIDUNGS_BY_MAVAITRO: (maVaiTro: string) =>
            `${BASE_URL}/quan-tri-vien/nguoi-dung/role/${maVaiTro}`
    },
    NHANVIEN: {
        GET_NHANVIENS: `${BASE_URL}/quan-tri-vien/nhan-vien/list`,
        GET_BY_MANGUOIDUNG: (maNguoiDung: string) =>
            `${BASE_URL}/quan-tri-vien/nhan-vien/${maNguoiDung}`,
        ADD: `${BASE_URL}/quan-tri-vien/nhan-vien/add`,
        UPDATE: (maNguoiDung: string) => `${BASE_URL}/quan-tri-vien/nhan-vien/edit/${maNguoiDung}`,
        DELETE: (maNguoiDung: string) =>
            `${BASE_URL}/quan-tri-vien/nhan-vien/delete/${maNguoiDung}`,
        BAN: (maNguoiDung: string) => `${BASE_URL}/quan-tri-vien/nhan-vien/ban/${maNguoiDung}`
    },
    VAITRO: {
        GET_VAITROS: `${BASE_URL}/quan-tri-vien/vai-tro/list`,
        GET_BY_MAVAITRO: (maVaiTro: string) =>
            `${BASE_URL}/quan-tri-vien/vai-tro/maVaiTro=${maVaiTro}`,
        ADD: `${BASE_URL}/quan-tri-vien/vai-tro/add`,
        UPDATE: (maVaiTro: string) => `${BASE_URL}/quan-tri-vien/vai-tro/edit/${maVaiTro}`
    },
    KHACHHANG: {
        GET_KHACHHANGS: `${BASE_URL}/quan-tri-vien/khach-hang/list`,
        GET_BY_MANGUOIDUNG: (maNguoiDung: string) =>
            `${BASE_URL}/quan-tri-vien/khach-hang/${maNguoiDung}`,
        ADD: `${BASE_URL}/quan-tri-vien/khach-hang/add`,
        UPDATE: (maNguoiDung: string) => `${BASE_URL}/quan-tri-vien/khach-hang/edit/${maNguoiDung}`,
        DELETE: (maNguoiDung: string) =>
            `${BASE_URL}/quan-tri-vien/khach-hang/delete/${maNguoiDung}`,
        BAN: (maNguoiDung: string) => `${BASE_URL}/quan-tri-vien/khach-hang/ban/${maNguoiDung}`
    },
    CHUCUAHANG: {
        GET_CHUCUAHANGS: `${BASE_URL}/nhan-vien/chu-cua-hang/list`,
        GET_BY_MANGUOIDUNG: (maNguoiDung: string) =>
            `${BASE_URL}/nhan-vien/chu-cua-hang/${maNguoiDung}`,
        ADD: `${BASE_URL}/nhan-vien/chu-cua-hang/add`,
        UPDATE: (maNguoiDung: string) => `${BASE_URL}/nhan-vien/chu-cua-hang/edit/${maNguoiDung}`,
        DELETE: (maNguoiDung: string) => `${BASE_URL}/nhan-vien/chu-cua-hang/delete/${maNguoiDung}`,
        BAN: (maNguoiDung: string) => `${BASE_URL}/nhan-vien/chu-cua-hang/ban/${maNguoiDung}`
    },
    LOAIKHIEUNAI: {
        GET_LOAIKHIEUNAIS: `${BASE_URL}/nhan-vien/loai-khieu-nai/list`,
        GET_BY_MALOAIKHIEUNAI: (maLoaiKhieuNai: string) =>
            `${BASE_URL}/nhan-vien/loai-khieu-nai/maLoaiKhieuNai=${maLoaiKhieuNai}`,
        ADD: `${BASE_URL}/nhan-vien/loai-khieu-nai/add`,
        UPDATE: (maLoaiKhieuNai: string) =>
            `${BASE_URL}/nhan-vien/loai-khieu-nai/edit/${maLoaiKhieuNai}`,
        DELETE: (maLoaiKhieuNai: string) =>
            `${BASE_URL}/nhan-vien/loai-khieu-nai/delete/${maLoaiKhieuNai}`
    },
    HOPDONG: {
        GET_HOPDONGS_BY_NHANVIEN: `${BASE_URL}/nhan-vien/hop-dong/list`,
        GET_HOPDONGS_BY_CHUCUAHANG: (maChuCuaHang: string) =>
            `${BASE_URL}/chu-cua-hang/hop-dong/chu-cua-hang/${maChuCuaHang}`,
        GET_BY_MAHOPDONG_BY_NHANVIEN: (maHopDong: string) =>
            `${BASE_URL}/nhan-vien/hop-dong/maHopDong=${maHopDong}`,
        GET_BY_MAHOPDONG_BY_CHUCUAHANG: (maHopDong: string) =>
            `${BASE_URL}/chu-cua-hang/hop-dong/maHopDong=${maHopDong}`,
        ADD: `${BASE_URL}/chu-cua-hang/hop-dong/add`,
        UPDATE: (maHopDong: string) => `${BASE_URL}/chu-cua-hang/hop-dong/edit/${maHopDong}`,
        DELETE: (maHopDong: string) => `${BASE_URL}/chu-cua-hang/hop-dong/delete/${maHopDong}`
    },
    DANHMUCXE: {
        GET_DANHMUCXES: `${BASE_URL}/nhan-vien/danh-muc-xe/list`,
        GET_BY_MADANHMUCXE: (maDanhMucXe: string) =>
            `${BASE_URL}/nhan-vien/danh-muc-xe/maDanhMucXe=${maDanhMucXe}`,
        ADD: `${BASE_URL}/nhan-vien/danh-muc-xe/add`,
        UPDATE: (maDanhMucXe: string) => `${BASE_URL}/nhan-vien/danh-muc-xe/edit/${maDanhMucXe}`,
        DELETE: (maDanhMucXe: string) => `${BASE_URL}/nhan-vien/danh-muc-xe/delete/${maDanhMucXe}`
    },
    XEMAY: {
        GET_XEMAYS_BY_NHANVIEN: `${BASE_URL}/nhan-vien/xe-may/list`,
        GET_BY_MAXEMAY_BY_NHANVIEN: (maXeMay: string) =>
            `${BASE_URL}/nhan-vien/xe-may/maXeMay=${maXeMay}`,
        ADD_BY_NHANVIEN: `${BASE_URL}/nhan-vien/xe-may/add`,
        UPDATE_BY_NHANVIEN: (maXeMay: string) => `${BASE_URL}/nhan-vien/xe-may/edit/${maXeMay}`,
        DELETE_BY_NHANVIEN: (maXeMay: string) => `${BASE_URL}/nhan-vien/xe-may/delete/${maXeMay}`,

        GET_XEMAYS_BY_CHUCUAHANG: (maChuCuaHang: string) =>
            `${BASE_URL}/chu-cua-hang/xe-may/chu-cua-hang/${maChuCuaHang}`,
        GET_BY_MAXEMAY_BY_CHUCUAHANG: (maXeMay: string) =>
            `${BASE_URL}/chu-cua-hang/xe-may/maXeMay=${maXeMay}`,
        ADD_BY_CHUCUAHANG: `${BASE_URL}/chu-cua-hang/xe-may/add`,
        UPDATE_BY_CHUCUAHANG: (maXeMay: string) =>
            `${BASE_URL}/chu-cua-hang/xe-may/edit/${maXeMay}`,
        DELETE_BY_CHUCUAHANG: (maXeMay: string) =>
            `${BASE_URL}/chu-cua-hang/xe-may/delete/${maXeMay}`
    },
    CHITIETDONHANG: {
        GET_CHITIETDONHANGS_BY_CHUCUAHANG: (maNguoiDung: string) =>
            `${BASE_URL}/chu-cua-hang/chi-tiet-don-hang/list/${maNguoiDung}`,
        GET_CHITIETDONHANGS_BY_TRANGTHAIDONHANG_CHUCUAHANG: (
            maNguoiDung: string,
            trangThaiDonHang: string
        ) => `${BASE_URL}/chu-cua-hang/chi-tiet-don-hang/list/${maNguoiDung}/${trangThaiDonHang}`,
        ADD_BY_CHUCUAHANG: `${BASE_URL}/chu-cua-hang/chi-tiet-don-hang/add`,
        UPDATE_BY_CHUCUAHANG: (maChiTietDonHang: string) =>
            `${BASE_URL}/chu-cua-hang/chi-tiet-don-hang/edit/${maChiTietDonHang}`,

        GET_CHITIETDONHANGS_BY_KHACHHANG: (maNguoiDung: string) =>
            `${BASE_URL}/khach-hang/chi-tiet-don-hang/list/${maNguoiDung}`,
        GET_CHITIETDONHANGS_BY_TRANGTHAIDONHANG_KHACHHANG: (
            maNguoiDung: string,
            trangThaiDonHang: string
        ) => `${BASE_URL}/khach-hang/chi-tiet-don-hang/list/${maNguoiDung}/${trangThaiDonHang}`,
        ADD_BY_KHACHHANG: `${BASE_URL}/khach-hang/chi-tiet-don-hang/add`,
        UPDATE_BY_KHACHHANG: (maChiTietDonHang: string) =>
            `${BASE_URL}/khach-hang/chi-tiet-don-hang/edit/${maChiTietDonHang}`
    },
    KHIEUNAI: {
        GET_KHIEUNAIS_BY_NHANVIEN: `${BASE_URL}/nhan-vien/khieu-nai/list`,
        GET_KHIEUNAIS_BY_CHUCUAHANG: (maNguoiDung: string) =>
            `${BASE_URL}/chu-cua-hang/khieu-nai/list/${maNguoiDung}`,
        GET_KHIEUNAIS_BY_KHACHHANG: (maNguoiDung: string) =>
            `${BASE_URL}/khach-hang/khieu-nai/list/${maNguoiDung}`,
        GET_BY_MAKHIEUNAI: (maKhieuNai: string) =>
            `${BASE_URL}/khach-hang/khieu-nai/maKhieuNai=${maKhieuNai}`,
        ADD: `${BASE_URL}/khach-hang/khieu-nai/add`,
        UPDATE: (maKhieuNai: string) => `${BASE_URL}/khach-hang/khieu-nai/edit/${maKhieuNai}`,
        DELETE: (maKhieuNai: string) => `${BASE_URL}/khach-hang/khieu-nai/delete/${maKhieuNai}`
    }
}
