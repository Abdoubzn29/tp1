package com.example.tp;

public class Purchase {

        private String item;
        private Double qty;

        public Purchase(String item, Double qty) {
            this.item = item;
            this.qty = qty;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public Double getQty() {
            return qty;
        }

        public void setQty(Double qty) {
            this.qty = qty;
        }

        public String toString() {
            return qty + " " + item;
        }
    }

