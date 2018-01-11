package dao;

import java.sql.SQLException;

import fw.Sanitizing;

public class Item {
	public Item() {}

	private int itemNo;
	private String itemCategoryCode;
	private String itemCategoryName;
	private String itemName;
	private String explanation;
	private int price;
	private int recommendFLG;
	private String lastUpdateDate;

	public void setItemCategoryNameById(String categoryId) {
		try {
			String categoryName = ItemDAO.getInstance().findCategoryName(categoryId);
			this.itemCategoryName = categoryName;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 以下、次のコメントまではフォーマットされた値を返すゲッター
	public String getFormattedItemNo() {
		String str = String.format("S%09d", itemNo);
		return str;
	}

	public String getFormattedItemName() {
		String itemName = Sanitizing.getInstance().againstXSS(this.itemName);
		itemName = itemName.replace(" ", "&nbsp;");
		return itemName;
	}

	public String getFormattedItemNameForTextBox() {
		String itemName = Sanitizing.getInstance().againstXSS(this.itemName);
		return itemName;
	}

	public String getFormattedExplanation() {
		String text = explanation;
		text = Sanitizing.getInstance().againstXSS(text);
		text = text.replaceAll("[\n]", "<br>\n");
		text = text.replaceAll("\t", "    ");
		text = text.replace(" ", "&nbsp;");
		return text;
	}

	public String getFormattedExplanationForTextArea() {
		String text = explanation;
		text = Sanitizing.getInstance().againstXSS(text);
		return text;
	}

	public String getFormattedPrice() {
		String str = String.format("%,d円", price);
		return str;
	}

	public String getFormattedRecommendFLG(int index) {
		String str = null;
		if (index == 0){
			if (recommendFLG == 1) {
				str = "★";
			} else if (recommendFLG == 0) {
				str = "-";
			}
		}
		if (index == 1) {
			if (recommendFLG == 1) {
				str = "おすすめ商品";
			} else if (recommendFLG == 0) {
				str = "通常商品";
			}
		}
		return str;
	}


// ====================以下アクセサメソッド=======================
	public int getItemNo() {
		return itemNo;
	}
	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemCategoryCode() {
		return itemCategoryCode;
	}
	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}
	public String getItemCategoryName() {
		return itemCategoryName;
	}
	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getRecommendFLG() {
		return recommendFLG;
	}
	public void setRecommendFLG(int recommendFLG) {
		this.recommendFLG = recommendFLG;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}


}
