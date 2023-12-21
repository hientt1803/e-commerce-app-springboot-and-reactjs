package com.poly.service.impl;

import com.poly.model.OrderDetail;
import com.poly.repository.OrderDetailDAO;
import com.poly.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailImpl implements OrderDetailService {
	@Autowired
	OrderDetailDAO orderDetailDAO;

	@Override
	public List<OrderDetail> findByOrderId(Long id) {
		return orderDetailDAO.findAllByOrder_Id(id);
	}

	@Override
	public List<OrderDetail> getTop10OrderDetail(Pageable pageable) {
		return orderDetailDAO.getTop10OrderDetailNonePageAble();
	}
}
