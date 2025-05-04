import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import DangKy from './components/auth/DangKy.vue'
import DangKyChuCuaHang from './components/auth/DangKyChuCuaHang.vue'
import DangNhap from './components/auth/DangNhap.vue'
import LoginSuccess from './components/auth/LoginSuccess.vue'
import { getNguoiDungByMaNguoiDung } from './services/authService'
import { getMaNguoiDung } from './services/localStorageService'
import ChiTietDonHangView from './views/chucuahang/donhang/ChiTietDonHangView.vue'
import DanhSachDonHangView from './views/chucuahang/donhang/DanhSachDonHangView.vue'
import DanhSachKhieuNaiView from './views/chucuahang/khieunai/DanhSachKhieuNaiView.vue'
import CapNhatNhanVienCuaHangView from './views/chucuahang/nhanvien/CapNhatNhanVienCuaHangView.vue'
import DanhSachNhanVienCuaHangView from './views/chucuahang/nhanvien/DanhSachNhanVienCuaHangView.vue'
import ThemMoiNhanVienCuaHangView from './views/chucuahang/nhanvien/ThemMoiNhanVienCuaHangView.vue'
import CapNhatXeMayView from './views/chucuahang/xemay/CapNhatXeMayView.vue'
import DanhSachXeMayView from './views/chucuahang/xemay/DanhSachXeMayView.vue'
import ThemMoiXeMayView from './views/chucuahang/xemay/ThemMoiXeMayView.vue'
import CapNhatThongTinCaNhanView from './views/khachhang/CapNhatThongTinCaNhanView.vue'
import ChiTietDonHangKhachHangView from './views/khachhang/ChiTietDonHangKhachHangView.vue'
import ChiTietXeMayTrangChuView from './views/khachhang/ChiTietXeMayTrangChuView.vue'
import DanhSachDonHangKhachHangView from './views/khachhang/DanhSachDonHangKhachHangView.vue'
import DanhSachXeMayTheoQuanView from './views/khachhang/DanhSachXeMayTheoQuanView.vue'
import GioHangView from './views/khachhang/GioHangView.vue'
import ThueXeMayView from './views/khachhang/ThueXeMayView.vue'
import TrangChuView from './views/khachhang/TrangChuView.vue'
import CapNhatTinhTrangXeView from './views/nhanvien/donhang/CapNhatTinhTrangXeView.vue'
import ChiTietDonHangNhanVienView from './views/nhanvien/donhang/ChiTietDonHangNhanVienView.vue'
import DanhSachDonHangNhanVienView from './views/nhanvien/donhang/DanhSachDonHangNhanVienView.vue'
import ChiTietCuaHangView from './views/quantrivien/cuahang/ChiTietCuaHangView.vue'
import DanhSachCuaHangView from './views/quantrivien/cuahang/DanhSachCuaHangView.vue'
import CapNhatDanhMucXeView from './views/quantrivien/danhmucxe/CapNhatDanhMucXeView.vue'
import DanhSachDanhMucXeView from './views/quantrivien/danhmucxe/DanhSachDanhMucXeView.vue'
import ThemMoiDanhMucXeView from './views/quantrivien/danhmucxe/ThemMoiDanhMucXeView.vue'
import CapNhatLoaiKhieuNaiView from './views/quantrivien/loaikhieunai/CapNhatLoaiKhieuNaiView.vue'
import DanhSachLoaiKhieuNaiView from './views/quantrivien/loaikhieunai/DanhSachLoaiKhieuNaiView.vue'
import ThemMoiLoaiKhieuNaiView from './views/quantrivien/loaikhieunai/ThemMoiLoaiKhieuNaiView.vue'
import DanhSachNguoiDungView from './views/quantrivien/nguoidung/DanhSachNguoiDungView.vue'
import CapNhatChuCuaHangView from './views/quantrivien/nguoidung/chucuahang/CapNhatChuCuaHangView.vue'
import ThemMoiChuCuaHangView from './views/quantrivien/nguoidung/chucuahang/ThemMoiChuCuaHangView.vue'
import CapNhatKhachHangView from './views/quantrivien/nguoidung/khachhang/CapNhatKhachHangView.vue'
import ThemMoiKhachHangView from './views/quantrivien/nguoidung/khachhang/ThemMoiKhachHangView.vue'
import CapNhatNhanVienView from './views/quantrivien/nguoidung/nhanvien/CapNhatNhanVienView.vue'
import ThemMoiNhanVienView from './views/quantrivien/nguoidung/nhanvien/ThemMoiNhanVienView.vue'
import CapNhatVaiTroView from './views/quantrivien/vaitro/CapNhatVaiTroView.vue'
import DanhSachVaiTroView from './views/quantrivien/vaitro/DanhSachVaiTroView.vue'
import ThemMoiVaiTroView from './views/quantrivien/vaitro/ThemMoiVaiTroView.vue'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        redirect: '/trang-chu'
    },
    {
        path: '/trang-chu',
        name: 'TrangChu',
        component: TrangChuView
    },
    {
        path: '/dang-ky',
        name: 'DangKy',
        component: DangKy
    },
    {
        path: '/dang-ky-chu-cua-hang',
        name: 'DangKyChuCuaHang',
        component: DangKyChuCuaHang
    },
    {
        path: '/dang-nhap',
        name: 'DangNhap',
        component: DangNhap
    },
    {
        path: '/login-success',
        name: 'LoginSuccess',
        component: LoginSuccess
    },
    {
        path: '/danh-sach-xe/:maQuanHuyen?',
        name: 'DanhSachXeMayTheoQuanView',
        component: DanhSachXeMayTheoQuanView
    },
    {
        path: '/chi-tiet/:maXeMay',
        name: 'ChiTietXeMayTrangChuView',
        component: ChiTietXeMayTrangChuView
    },
    {
        path: '/thue-xe/:maXeMay',
        name: 'ThueXeMayView',
        component: ThueXeMayView
    },
    {
        path: '/gio-hang',
        name: 'GioHangView',
        component: GioHangView
    },
    {
        path: '/nguoi-dung/cap-nhat/:maNguoiDung',
        name: 'CapNhatThongTinCaNhanView',
        component: CapNhatThongTinCaNhanView
    },
    {
        path: '/khach-hang/don-hang/danh-sach/:trangThaiDonHang',
        name: 'DanhSachDonHangKhachHangView',
        component: DanhSachDonHangKhachHangView
    },
    {
        path: '/khach-hang/don-hang/chi-tiet/:trangThaiDonHang/:maDonHang',
        name: 'ChiTietDonHangKhachHangView',
        component: ChiTietDonHangKhachHangView
    },
    {
        path: '/quan-tri/vai-tro/danh-sach',
        name: 'DanhSachVaiTroView',
        component: DanhSachVaiTroView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
    },
    {
        path: '/quan-tri/vai-tro/them-moi',
        name: 'ThemMoiVaiTroView',
        component: ThemMoiVaiTroView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
    },
    {
        path: '/quan-tri/vai-tro/cap-nhat/:maVaiTro',
        name: 'CapNhatVaiTroView',
        component: CapNhatVaiTroView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
    },
    {
        path: '/quan-tri/nguoi-dung/danh-sach/:maVaiTro',
        name: 'DanhSachNguoiDungView',
        component: DanhSachNguoiDungView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
    },
    {
        path: '/quan-tri/nguoi-dung/them-moi/:maVaiTro',
        name: 'ThemMoiChuCuaHangView',
        component: ThemMoiChuCuaHangView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
    },
    {
        path: '/quan-tri/nguoi-dung/cap-nhat/:maVaiTro/:maNguoiDung',
        name: 'CapNhatChuCuaHangView',
        component: CapNhatChuCuaHangView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
    },
    {
        path: '/quan-tri/nguoi-dung/them-moi/:maVaiTro',
        name: 'ThemMoiNhanVienView',
        component: ThemMoiNhanVienView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
    },
    {
        path: '/quan-tri/nguoi-dung/cap-nhat/:maVaiTro/:maNguoiDung',
        name: 'CapNhatNhanVienView',
        component: CapNhatNhanVienView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
    },
    {
        path: '/quan-tri/nguoi-dung/them-moi/:maVaiTro',
        name: 'ThemMoiKhachHangView',
        component: ThemMoiKhachHangView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
    },
    {
        path: '/quan-tri/nguoi-dung/cap-nhat/:maVaiTro/:maNguoiDung',
        name: 'CapNhatKhachHangView',
        component: CapNhatKhachHangView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
    },
    {
        path: '/quan-tri/cua-hang/danh-sach',
        name: 'DanhSachCuaHangView',
        component: DanhSachCuaHangView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN'] }
    },
    {
        path: '/quan-tri/cua-hang/chi-tiet/:maChuCuaHang',
        name: 'ChiTietCuaHangView',
        component: ChiTietCuaHangView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN'] }
    },
    {
        path: '/quan-tri/loai-khieu-nai/danh-sach',
        name: 'DanhSachLoaiKhieuNaiView',
        component: DanhSachLoaiKhieuNaiView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/quan-tri/loai-khieu-nai/them-moi/',
        name: 'ThemMoiLoaiKhieuNaiView',
        component: ThemMoiLoaiKhieuNaiView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/quan-tri/loai-khieu-nai/cap-nhat/:maLoaiKhieuNai',
        name: 'CapNhatLoaiKhieuNaiView',
        component: CapNhatLoaiKhieuNaiView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/quan-tri/danh-muc-xe/danh-sach',
        name: 'DanhSachDanhMucXeView',
        component: DanhSachDanhMucXeView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/quan-tri/danh-muc-xe/them-moi',
        name: 'ThemMoiDanhMucXeView',
        component: ThemMoiDanhMucXeView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/quan-tri/danh-muc-xe/cap-nhat/:maDanhMucXe',
        name: 'CapNhatDanhMucXeView',
        component: CapNhatDanhMucXeView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/chu-cua-hang/xe-may/danh-sach',
        name: 'DanhSachXeMayView',
        component: DanhSachXeMayView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/chu-cua-hang/xe-may/them-moi',
        name: 'ThemMoiXeMayView',
        component: ThemMoiXeMayView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/chu-cua-hang/xe-may/cap-nhat/:maXeMay',
        name: 'CapNhatXeMayView',
        component: CapNhatXeMayView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/chu-cua-hang/nhan-vien/danh-sach',
        name: 'DanhSachNhanVienCuaHangView',
        component: DanhSachNhanVienCuaHangView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/chu-cua-hang/nhan-vien/them-moi',
        name: 'ThemMoiNhanVienCuaHangView',
        component: ThemMoiNhanVienCuaHangView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/chu-cua-hang/nhan-vien/cap-nhat/:maNhanVien',
        name: 'CapNhatNhanVienCuaHangView',
        component: CapNhatNhanVienCuaHangView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/chu-cua-hang/don-hang/danh-sach/:trangThaiDonHang',
        name: 'DanhSachDonHangView',
        component: DanhSachDonHangView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/chu-cua-hang/don-hang/chi-tiet/:trangThaiDonHang/:maDonHang',
        name: 'ChiTietDonHangView',
        component: ChiTietDonHangView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/chu-cua-hang/khieu-nai/danh-sach',
        name: 'DanhSachKhieuNaiView',
        component: DanhSachKhieuNaiView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG'] }
    },
    {
        path: '/nhan-vien/don-hang/danh-sach/:trangThaiDonHang',
        name: 'DanhSachDonHangNhanVienView',
        component: DanhSachDonHangNhanVienView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/nhan-vien/don-hang/chi-tiet/:trangThaiDonHang/:maDonHang',
        name: 'ChiTietDonHangNhanVienView',
        component: ChiTietDonHangNhanVienView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/nhan-vien/don-hang/cap-nhat/:trangThaiDonHang/:maDonHang/:maXeMay',
        name: 'CapNhatTinhTrangXeView',
        component: CapNhatTinhTrangXeView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_CHUCUAHANG', 'ROLE_NHANVIEN'] }
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

router.beforeEach(async (to, from, next) => {
    if (!to.meta.requiresRole) {
        return next()
    }

    try {
        const result = await getNguoiDungByMaNguoiDung(getMaNguoiDung())
        const hasVaiTroQuanTri =
            result.vaiTro.tenVaiTro.includes('ROLE_QUANTRIVIEN') ||
            result.vaiTro.tenVaiTro.includes('ROLE_CHUCUAHANG') ||
            result.vaiTro.tenVaiTro.includes('ROLE_NHANVIEN')

        if (!hasVaiTroQuanTri) {
            return next('/')
        }

        next()
    } catch (error) {
        console.error('Lỗi khi lấy thông tin người dùng:', error)
        next('/')
    }
})

export default router
