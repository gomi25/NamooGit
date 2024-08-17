package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.GiftDto;
import dto.GiftPayDto;
import dto.GiftReceivedAmountDto;

public class GiftDao {
    private Connection getConnection() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "namoo";
        String pw = "7777";

        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, id, pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 사용하지 않음
    // getRecommendedProducts() : 추천 상품 리스트를 가져옴
    // 리턴 값 : 추천 상품 리스트 (List<GiftDto>)
    public List<GiftDto> getRecommendedProducts() throws Exception {
        List<GiftDto> recommendedProducts = new ArrayList<>();
        String sql = "SELECT g.gift_idx AS 상품ID, b.brand_name AS 브랜드명, g.gift_name AS 상품명, " +
                "g.gift_img_url AS 상품이미지url, g.price AS 상품가격 " +
                "FROM recommended_product rp " +
                "JOIN gift g ON rp.gift_idx = g.gift_idx " +
                "JOIN brand_name b ON g.brand_name_idx = b.brand_name_idx";

        Connection conn = getConnection();  
        PreparedStatement pstmt = conn.prepareStatement(sql);  
        ResultSet rs = pstmt.executeQuery(); 

        while (rs.next()) {
            GiftDto giftDTO = new GiftDto();
            giftDTO.setGiftIdx(rs.getInt("상품ID"));
            giftDTO.setBrandName(rs.getString("브랜드명"));
            giftDTO.setGiftName(rs.getString("상품명"));
            giftDTO.setGiftImgUrl(rs.getString("상품이미지url"));
            giftDTO.setPrice(rs.getInt("상품가격"));
            recommendedProducts.add(giftDTO);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return recommendedProducts;
    }

    // 사용하지 않음
    // getProductsByCategory(int categoryIdx) : 카테고리별 상품 리스트를 가져옴
    // categoryIdx : 카테고리의 인덱스 (int)
    // 리턴 값 : 해당 카테고리의 상품 리스트 (List<GiftDto>)
    public List<GiftDto> getProductsByCategory(int categoryIdx) throws Exception {
        List<GiftDto> products = new ArrayList<>();
        String sql = "SELECT g.gift_idx AS 상품ID, b.brand_name AS 브랜드명, g.gift_name AS 상품명, " +
                "g.gift_img_url AS 상품이미지url, g.price AS 상품가격, " +
                "g.reduced_price AS 세일가격 " +
                "FROM gift g " +
                "JOIN brand_name b ON g.brand_name_idx = b.brand_name_idx " +
                "WHERE theme_category_idx = ?";

        Connection conn = getConnection(); 
        PreparedStatement pstmt = conn.prepareStatement(sql);  
        pstmt.setInt(1, categoryIdx);  
        ResultSet rs = pstmt.executeQuery(); 

        while (rs.next()) {
            GiftDto giftDTO = new GiftDto();
            giftDTO.setGiftIdx(rs.getInt("상품ID"));
            giftDTO.setBrandName(rs.getString("브랜드명"));
            giftDTO.setGiftName(rs.getString("상품명"));
            giftDTO.setGiftImgUrl(rs.getString("상품이미지url"));
            giftDTO.setPrice(rs.getInt("상품가격"));
            int reducedPrice = rs.getInt("세일가격");
            if (rs.wasNull()) {
                giftDTO.setReducedPrice(null);  // 세일 가격이 null인 경우 처리
            } else {
                giftDTO.setReducedPrice(reducedPrice);
            }
            products.add(giftDTO);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return products;
    }

    // 사용하지 않음
    // countProductsByCategory(int categoryIdx) : 카테고리별 상품 개수를 카운트
    // categoryIdx : 카테고리의 인덱스 (int)
    // 리턴 값 : 해당 카테고리의 상품 개수 (int)
    public int countProductsByCategory(int categoryIdx) throws Exception {
        String sql = "SELECT COUNT(*) FROM gift WHERE theme_category_idx = ?";
        int count = 0;

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, categoryIdx);  
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            count = rs.getInt(1); 
        }

        rs.close();
        pstmt.close();
        conn.close();

        return count;
    }

    // getProductsByName() : 상품명순으로 전체 상품 리스트를 가져옴
    // 리턴 값 : 상품명순으로 정렬된 상품 리스트 (ArrayList<GiftDto>)
    public ArrayList<GiftDto> getProductsByName() throws Exception {
        ArrayList<GiftDto> products = new ArrayList<>();
        String sql = "SELECT g.gift_idx AS 상품ID, g.gift_name AS 상품명, g.gift_img_url AS 상품이미지, b.brand_name AS 브랜드명, " +
                "g.price AS 가격, g.reduced_price AS 세일가격 " +
                "FROM gift g JOIN brand_name b " +
                "ON g.brand_name_idx = b.brand_name_idx " +
                "ORDER BY g.gift_name ASC"; 

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            GiftDto giftDTO = new GiftDto();
            giftDTO.setGiftIdx(rs.getInt("상품ID"));
            giftDTO.setBrandName(rs.getString("브랜드명"));
            giftDTO.setGiftName(rs.getString("상품명"));
            giftDTO.setGiftImgUrl(rs.getString("상품이미지"));
            giftDTO.setPrice(rs.getInt("가격"));
            int reducedPrice = rs.getInt("세일가격");
            if (rs.wasNull()) {
                giftDTO.setReducedPrice(null);  // 세일 가격이 null인 경우 처리
            } else {
                giftDTO.setReducedPrice(reducedPrice);
            }
            products.add(giftDTO);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return products;
    }

    // 사용하지 않음
    // getProductsByHighestPrice() : 높은 가격순으로 상품 리스트를 가져옴
    // 리턴 값 : 높은 가격순으로 정렬된 상품 리스트 (List<GiftDto>)
    public List<GiftDto> getProductsByHighestPrice() throws Exception {
        List<GiftDto> products = new ArrayList<>();
        String sql = "SELECT g.gift_idx AS 상품ID, g.gift_name AS 상품명, g.gift_img_url AS 상품이미지, b.brand_name AS 브랜드명, " +
                "g.price AS 가격, g.reduced_price AS 세일가격 " +
                "FROM gift g JOIN brand_name b " +
                "ON g.brand_name_idx = b.brand_name_idx " +
                "WHERE g.theme_category_idx = 1 " +
                "ORDER BY g.price DESC"; 

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            GiftDto giftDTO = new GiftDto();
            giftDTO.setGiftIdx(rs.getInt("상품ID"));
            giftDTO.setBrandName(rs.getString("브랜드명"));
            giftDTO.setGiftName(rs.getString("상품명"));
            giftDTO.setGiftImgUrl(rs.getString("상품이미지"));
            giftDTO.setPrice(rs.getInt("가격"));
            int reducedPrice = rs.getInt("세일가격");
            if (rs.wasNull()) {
                giftDTO.setReducedPrice(null);  // 세일 가격이 null인 경우 처리
            } else {
                giftDTO.setReducedPrice(reducedPrice);
            }
            products.add(giftDTO);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return products;
    }
    
    // 사용하지 않음
    // getProductsByLowestPrice() : 낮은 가격순으로 상품 리스트를 가져옴
    // 리턴 값 : 낮은 가격순으로 정렬된 상품 리스트 (List<GiftDto>)
    public List<GiftDto> getProductsByLowestPrice() throws Exception {
        List<GiftDto> products = new ArrayList<>();
        String sql = "SELECT g.gift_idx AS 상품ID, g.gift_name AS 상품명, g.gift_img_url AS 상품이미지, b.brand_name AS 브랜드명, " +
                "g.price AS 가격, g.reduced_price AS 세일가격 " +
                "FROM gift g JOIN brand_name b " +
                "ON g.brand_name_idx = b.brand_name_idx " +
                "WHERE g.theme_category_idx = 1 " +
                "ORDER BY g.price ASC";  

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            GiftDto giftDTO = new GiftDto();
            giftDTO.setGiftIdx(rs.getInt("상품ID"));
            giftDTO.setBrandName(rs.getString("브랜드명"));
            giftDTO.setGiftName(rs.getString("상품명"));
            giftDTO.setGiftImgUrl(rs.getString("상품이미지"));
            giftDTO.setPrice(rs.getInt("가격"));
            int reducedPrice = rs.getInt("세일가격");
            if (rs.wasNull()) {
                giftDTO.setReducedPrice(null);
            } else {
                giftDTO.setReducedPrice(reducedPrice);
            }
            products.add(giftDTO);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return products;
    }

    // 사용하지 않음
    // getProductsByKeywordByName(String keyword) : 상품명순으로 키워드로 상품을 검색
    // keyword : 검색할 키워드 (String)
    // 리턴 값 : 상품명순으로 정렬된 검색된 상품 리스트 (List<GiftDto>)
    public List<GiftDto> getProductsByKeyword(String keyword) throws Exception {
        List<GiftDto> products = new ArrayList<>();
        String sql = "SELECT g.gift_idx AS 상품ID, g.gift_name AS 상품명, g.gift_img_url AS 상품이미지, b.brand_name AS 브랜드명, " +
                "g.price AS 가격 " +
                "FROM gift g JOIN brand_name b " +
                "ON g.brand_name_idx = b.brand_name_idx " +
                "WHERE g.gift_name LIKE ? OR b.brand_name LIKE ?";

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        String queryKeyword = "%" + keyword + "%";
        pstmt.setString(1, queryKeyword);  
        pstmt.setString(2, queryKeyword);  
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            GiftDto giftDTO = new GiftDto();
            giftDTO.setGiftIdx(rs.getInt("상품ID"));
            giftDTO.setBrandName(rs.getString("브랜드명"));
            giftDTO.setGiftName(rs.getString("상품명"));
            giftDTO.setGiftImgUrl(rs.getString("상품이미지"));
            giftDTO.setPrice(rs.getInt("가격"));
            products.add(giftDTO);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return products;
    }

    // ==================== 키워드로 검색된 상품 개수 카운트 ====================
    public int countProductsByKeyword(String keyword) throws Exception {
        String sql = "SELECT COUNT(*) FROM gift g " +
                "JOIN brand_name b ON g.brand_name_idx = b.brand_name_idx " +
                "WHERE g.gift_name LIKE ? OR b.brand_name LIKE ?";
        int count = 0;

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        String queryKeyword = "%" + keyword + "%";
        pstmt.setString(1, queryKeyword);
        pstmt.setString(2, queryKeyword);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            count = rs.getInt(1);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return count;
    }

    // 사용하지 않음
    // getProductsByKeywordByName(String keyword) : 상품명순으로 키워드로 상품을 검색
    // keyword : 검색할 키워드 (String)
    // 리턴 값 : 상품명순으로 정렬된 검색된 상품 리스트 (List<GiftDto>)
    public List<GiftDto> getProductsByKeywordByName(String keyword) throws Exception {
        List<GiftDto> products = new ArrayList<>();
        String sql = "SELECT g.gift_idx AS 상품ID, g.gift_name AS 상품명, g.gift_img_url AS 상품이미지, b.brand_name AS 브랜드명, " +
                "g.price AS 가격, g.reduced_price AS 세일가격 " +
                "FROM gift g JOIN brand_name b " +
                "ON g.brand_name_idx = b.brand_name_idx " +
                "WHERE g.gift_name LIKE ? OR b.brand_name LIKE ? " +
                "ORDER BY g.gift_name ASC"; 

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        String queryKeyword = "%" + keyword + "%";
        pstmt.setString(1, queryKeyword);  
        pstmt.setString(2, queryKeyword);  
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            GiftDto giftDTO = new GiftDto();
            giftDTO.setGiftIdx(rs.getInt("상품ID"));
            giftDTO.setBrandName(rs.getString("브랜드명"));
            giftDTO.setGiftName(rs.getString("상품명"));
            giftDTO.setGiftImgUrl(rs.getString("상품이미지"));
            giftDTO.setPrice(rs.getInt("가격"));
            int reducedPrice = rs.getInt("세일가격");
            if (rs.wasNull()) {
                giftDTO.setReducedPrice(null);  // 세일 가격이 null인 경우 처리
            } else {
                giftDTO.setReducedPrice(reducedPrice);
            }
            products.add(giftDTO);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return products;
    }

    // 사용하지 않음
    // getProductsByKeywordByHighestPrice(String keyword) : 높은 가격순으로 키워드로 상품을 검색
    // keyword : 검색할 키워드 (String)
    // 리턴 값 : 높은 가격순으로 정렬된 검색된 상품 리스트 (List<GiftDto>)
    public List<GiftDto> getProductsByKeywordByHighestPrice(String keyword) throws Exception {
        List<GiftDto> products = new ArrayList<>();
        String sql = "SELECT g.gift_idx AS 상품ID, g.gift_name AS 상품명, g.gift_img_url AS 상품이미지, b.brand_name AS 브랜드명, " +
                "g.price AS 가격, g.reduced_price AS 세일가격 " +
                "FROM gift g JOIN brand_name b " +
                "ON g.brand_name_idx = b.brand_name_idx " +
                "WHERE g.gift_name LIKE ? OR b.brand_name LIKE ? " +
                "ORDER BY g.price DESC"; 

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        String queryKeyword = "%" + keyword + "%";
        pstmt.setString(1, queryKeyword); 
        pstmt.setString(2, queryKeyword);  
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            GiftDto giftDTO = new GiftDto();
            giftDTO.setGiftIdx(rs.getInt("상품ID"));
            giftDTO.setBrandName(rs.getString("브랜드명"));
            giftDTO.setGiftName(rs.getString("상품명"));
            giftDTO.setGiftImgUrl(rs.getString("상품이미지"));
            giftDTO.setPrice(rs.getInt("가격"));
            int reducedPrice = rs.getInt("세일가격");
            if (rs.wasNull()) {
                giftDTO.setReducedPrice(null);  // 세일 가격이 null인 경우 처리
            } else {
                giftDTO.setReducedPrice(reducedPrice);
            }
            products.add(giftDTO);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return products;
    }

    // 사용하지 않음
    // getProductsByKeywordByLowestPrice(String keyword) : 낮은 가격순으로 키워드로 상품을 검색
    // keyword : 검색할 키워드 (String)
    // 리턴 값 : 낮은 가격순으로 정렬된 검색된 상품 리스트 (List<GiftDto>)
    public List<GiftDto> getProductsByKeywordByLowestPrice(String keyword) throws Exception {
        List<GiftDto> products = new ArrayList<>();
        String sql = "SELECT g.gift_idx AS 상품ID, g.gift_name AS 상품명, g.gift_img_url AS 상품이미지, b.brand_name AS 브랜드명, " +
                "g.price AS 가격, g.reduced_price AS 세일가격 " +
                "FROM gift g JOIN brand_name b " +
                "ON g.brand_name_idx = b.brand_name_idx " +
                "WHERE g.gift_name LIKE ? OR b.brand_name LIKE ? " +
                "ORDER BY g.price ASC";  

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        String queryKeyword = "%" + keyword + "%";
        pstmt.setString(1, queryKeyword);  
        pstmt.setString(2, queryKeyword);  
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            GiftDto giftDTO = new GiftDto();
            giftDTO.setGiftIdx(rs.getInt("상품ID"));
            giftDTO.setBrandName(rs.getString("브랜드명"));
            giftDTO.setGiftName(rs.getString("상품명"));
            giftDTO.setGiftImgUrl(rs.getString("상품이미지"));
            giftDTO.setPrice(rs.getInt("가격"));
            int reducedPrice = rs.getInt("세일가격");
            if (rs.wasNull()) {
                giftDTO.setReducedPrice(null); 
            } else {
                giftDTO.setReducedPrice(reducedPrice);
            }
            products.add(giftDTO);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return products;
    }
    
    // insertGiftPay(GiftPayDto giftPayDto) : 선물 결제 정보 인서트
    // giftPayDto : 결제 정보를 담고 있는 GiftPayDto 객체
    // 리턴 값 : 생성된 결제 정보의 payment_idx 값 (int)
    public int insertGiftPay(GiftPayDto giftPayDto) throws Exception {
        int retValue = 0; // 변수 선언
        String sql = "INSERT INTO gift_pay(PAYMENT_IDX, MEMBER_IDX, GIFT_IDX, PAYMENT_DATE, AGREE1, AGREE2, AGREE3, MESSAGE) "
                + "VALUES(seq_payment_idx.nextval, ?, ?, sysdate, ?, ?, ?, ?)";

        Connection conn = getConnection();
        String[] arrColNames = { "payment_idx" };
        PreparedStatement pstmt = conn.prepareStatement(sql, arrColNames);

        pstmt.setInt(1, giftPayDto.getMemberIdx());
        pstmt.setInt(2, giftPayDto.getGiftIdx());
        pstmt.setInt(3, giftPayDto.getAgree1());
        pstmt.setInt(4, giftPayDto.getAgree2());
        pstmt.setInt(5, giftPayDto.getAgree3());
        pstmt.setString(6, giftPayDto.getMessage());
        pstmt.executeUpdate();

        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            retValue = rs.getInt(1);
        }
        rs.close();
        pstmt.close();
        conn.close();

        return retValue;
    }

    // insertGiftReceivedAmount(GiftReceivedAmountDto giftReceivedAmountDto) : 선물 받은 사람과 수량 정보를 인서트
    // giftReceivedAmountDto : 선물 받은 사람과 수량 정보를 담고 있는 GiftReceivedAmountDto 객체
    public void insertGiftReceivedAmount(GiftReceivedAmountDto giftReceivedAmountDto) throws Exception {
        String sql = "INSERT INTO gift_received_amount(PAYMENT_IDX, RECEIVED_NAME, RECEIVED_PHONE, QTY) "
                + "VALUES(?, ?, ?, ?)";

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, giftReceivedAmountDto.getPaymentIdx());
        pstmt.setString(2, giftReceivedAmountDto.getReceivedName());
        pstmt.setString(3, giftReceivedAmountDto.getReceivedPhone());
        pstmt.setInt(4, giftReceivedAmountDto.getQty());
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }
    
    // getProductById(int giftIdx) : 특정 상품의 상세 정보를 가져옴
    // giftIdx : 가져올 상품의 인덱스 (int)
    // 리턴 값 : 해당 상품의 상세 정보 (GiftDto)
    public GiftDto getProductById(int giftIdx) throws Exception {
        GiftDto product = new GiftDto();
        String sql = "SELECT g.gift_idx AS 상품ID, g.gift_name AS 상품명, g.gift_img_url AS 상품이미지, b.brand_name AS 브랜드명, "
                   + "g.price AS 가격, g.reduced_price AS 세일가격 "
                   + "FROM gift g JOIN brand_name b "
                   + "ON g.brand_name_idx = b.brand_name_idx "
                   + "WHERE g.gift_idx = ?";

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, giftIdx);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            product.setGiftIdx(rs.getInt("상품ID"));
            product.setBrandName(rs.getString("브랜드명"));
            product.setGiftName(rs.getString("상품명"));
            product.setGiftImgUrl(rs.getString("상품이미지"));
            product.setPrice(rs.getInt("가격"));
            int reducedPrice = rs.getInt("세일가격");
            if (rs.wasNull()) {
                product.setReducedPrice(null);
            } else {
                product.setReducedPrice(reducedPrice);
            }
        }

        rs.close();
        pstmt.close();
        conn.close();

        return product;
    }
    
    // getProductsByPage(int page, int pageSize) : 페이지네이션을 통해 상품 리스트를 가져옴
    // page : 요청된 페이지 번호 (int)
    // pageSize : 한 페이지에 표시할 상품 수 (int)
    // 리턴 값 : 해당 페이지의 상품 리스트 (ArrayList<GiftDto>)
    public ArrayList<GiftDto> getProductsByPage(int page, int pageSize) throws SQLException {
        ArrayList<GiftDto> productList = new ArrayList<>();
        String sql = "SELECT * FROM gifts LIMIT ? OFFSET ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pageSize);
            pstmt.setInt(2, (page - 1) * pageSize);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    GiftDto gift = new GiftDto();
                    gift.setGiftIdx(rs.getInt("gift_idx"));
                    gift.setGiftName(rs.getString("gift_name"));
                    gift.setGiftImgUrl(rs.getString("gift_img_url"));
                    gift.setBrandName(rs.getString("brand_name"));
                    gift.setPrice(rs.getInt("price"));
                    gift.setReducedPrice(rs.getInt("reduced_price"));
                    productList.add(gift);
                }
            }
        }
        return productList;
    }
}
