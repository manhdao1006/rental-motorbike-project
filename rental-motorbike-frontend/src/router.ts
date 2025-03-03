import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import DangKy from './components/auth/DangKy.vue'
import DangNhap from './components/auth/DangNhap.vue'
import { getNguoiDungByMaNguoiDung } from './services/authService'
import { getMaNguoiDung } from './services/localStorageService'
import CapNhatDanhMucXeView from './views/nhanvien/danhmucxe/CapNhatDanhMucXeView.vue'
import DanhSachDanhMucXeView from './views/nhanvien/danhmucxe/DanhSachDanhMucXeView.vue'
import ThemMoiDanhMucXeView from './views/nhanvien/danhmucxe/ThemMoiDanhMucXeView.vue'
import DanhSachHopDongView from './views/nhanvien/hopdong/DanhSachHopDongView.vue'
import DanhSachKhieuNaiView from './views/nhanvien/khieunai/DanhSachKhieuNaiView.vue'
import CapNhatLoaiKhieuNaiView from './views/nhanvien/loaikhieunai/CapNhatLoaiKhieuNaiView.vue'
import DanhSachLoaiKhieuNaiView from './views/nhanvien/loaikhieunai/DanhSachLoaiKhieuNaiView.vue'
import ThemMoiLoaiKhieuNaiView from './views/nhanvien/loaikhieunai/ThemMoiLoaiKhieuNaiView.vue'
import CapNhatXeMayView from './views/nhanvien/xemay/CapNhatXeMayView.vue'
import ChiTietXeMayView from './views/nhanvien/xemay/ChiTietXeMayView.vue'
import DanhSachXeMayView from './views/nhanvien/xemay/DanhSachXeMayView.vue'
import ThemMoiXeMayView from './views/nhanvien/xemay/ThemMoiXeMayView.vue'
import DashBoardView from './views/quantrivien/DashBoardView.vue'
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
        path: '/dang-ky',
        name: 'DangKy',
        component: DangKy
    },
    {
        path: '/dang-nhap',
        name: 'DangNhap',
        component: DangNhap
    },
    {
        path: '/quan-tri/dashboard',
        name: 'DashBoardView',
        component: DashBoardView,
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
        name: 'ThemMoiNhanVienView',
        component: ThemMoiNhanVienView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
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
        path: '/quan-tri/nguoi-dung/them-moi/:maVaiTro',
        name: 'ThemMoiChuCuaHangView',
        component: ThemMoiChuCuaHangView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/quan-tri/loai-khieu-nai/them-moi/',
        name: 'ThemMoiLoaiKhieuNaiView',
        component: ThemMoiLoaiKhieuNaiView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/quan-tri/nguoi-dung/them-moi/:maVaiTro',
        name: 'ThemMoiKhachHangView',
        component: ThemMoiKhachHangView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
    },
    {
        path: '/quan-tri/nguoi-dung/cap-nhat/:maVaiTro/:maNguoiDung',
        name: 'CapNhatNhanVienView',
        component: CapNhatNhanVienView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
    },
    {
        path: '/quan-tri/loai-khieu-nai/cap-nhat/:maLoaiKhieuNai',
        name: 'CapNhatLoaiKhieuNaiView',
        component: CapNhatLoaiKhieuNaiView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/quan-tri/nguoi-dung/cap-nhat/:maVaiTro/:maNguoiDung',
        name: 'CapNhatChuCuaHangView',
        component: CapNhatChuCuaHangView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/quan-tri/nguoi-dung/cap-nhat/:maVaiTro/:maNguoiDung',
        name: 'CapNhatKhachHangView',
        component: CapNhatKhachHangView,
        meta: { requiresRole: 'ROLE_QUANTRIVIEN' }
    },
    {
        path: '/quan-tri/danh-muc-xe/danh-sach',
        name: 'DanhSachDanhMucXeView',
        component: DanhSachDanhMucXeView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/quan-tri/khieu-nai/danh-sach',
        name: 'DanhSachKhieuNaiView',
        component: DanhSachKhieuNaiView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/quan-tri/hop-dong/danh-sach',
        name: 'DanhSachHopDongView',
        component: DanhSachHopDongView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/quan-tri/loai-khieu-nai/danh-sach',
        name: 'DanhSachLoaiKhieuNaiView',
        component: DanhSachLoaiKhieuNaiView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/quan-tri/danh-muc-xe/them-moi',
        name: 'ThemMoiDanhMucXeView',
        component: ThemMoiDanhMucXeView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/quan-tri/danh-muc-xe/cap-nhat/:maDanhMucXe',
        name: 'CapNhatDanhMucXeView',
        component: CapNhatDanhMucXeView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/quan-tri/xe-may/danh-sach',
        name: 'DanhSachXeMayView',
        component: DanhSachXeMayView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/quan-tri/xe-may/them-moi',
        name: 'ThemMoiXeMayView',
        component: ThemMoiXeMayView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/quan-tri/xe-may/cap-nhat/:maXeMay',
        name: 'CapNhatXeMayView',
        component: CapNhatXeMayView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
    },
    {
        path: '/quan-tri/xe-may/chi-tiet/:maXeMay',
        name: 'ChiTietXeMayView',
        component: ChiTietXeMayView,
        meta: { requiresRole: ['ROLE_QUANTRIVIEN', 'ROLE_NHANVIEN'] }
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
