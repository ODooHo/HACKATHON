# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter
from pymongo import MongoClient

class IPOPipeline:
    def __init__(self):
        self.announce_list = []
        self.item_count = 0

    def process_item(self, item, spider):
        self.announce_list.append(item)
        return item

    def close_spider(self, spider):
        print(self.announce_list)
        if(len(self.announce_list) == 0):
            return

        for i in range(len(self.announce_list)):
            print("i : {}".format(self.announce_list),end="\n")