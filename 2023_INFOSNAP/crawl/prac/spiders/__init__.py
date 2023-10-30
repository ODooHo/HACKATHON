import scrapy
from scrapy import Spider
from .. import items
from pymongo import MongoClient
from bs4 import BeautifulSoup
from scrapy.http import HtmlResponse  # 추가


class StockSpider(Spider):
    name = 'base'
    announce_list = []

    def start_requests(self):
        url = "https://www.skku.edu/skku/campus/skk_comm/notice01.do"
        yield scrapy.Request(url, self.parse_start)

    def parse_start(self, response):
        # Find the total number of items and generate indices for the URLs
        total = 10

        base_url = "https://www.skku.edu/skku/campus/skk_comm/notice01.do"

        # Generate the link xpath
        for index in range(1, 5):
            title_xpath = f'//*[@id="jwxe_main_content"]/div/div/div[1]/div[1]/ul/li[{index}]/dl/dt/a/text()'
            title = response.xpath(title_xpath).get().strip()

            link_xpath = f'//*[@id="jwxe_main_content"]/div/div/div[1]/div[1]/ul/li[{index}]/dl/dt/a/@href'
            link = response.xpath(link_xpath).get()

            if link:
                full_link_url = f'{base_url}{link}'
                item = items.annItem()
                item['title'] = title
                item['link'] = full_link_url  # 링크와 함께 제목도 저장
                yield scrapy.Request(full_link_url, callback=self.parse_item, meta={'item': item})

    def parse_item(self, response):
        soup = BeautifulSoup(response.text, 'html.parser')
        item = response.meta['item']
        
        if 'content' in item:
            content = item['content']
        else:
            content = ''
        
        text = soup.find_all('dd')
        for p in text:
            content += p.get_text(strip=True).replace('\r', '').replace('\n', '')
        
        # 내용을 가져와서 item에 저장
        item['content'] = content
        
        yield item

        
            

