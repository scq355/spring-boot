package com.wowjoy.boot.elasticsearch;

import com.wowjoy.boot.elasticsearch.bean.Book;
import com.wowjoy.boot.elasticsearch.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class BootElasticsearchSpringDataTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testIndex() {
        Book book = new Book()
                .setBookName("西游记")
                .setAuthor("吴承恩")
                .setId(1);
        bookRepository.index(book);
    }

    @Test
    public void testFindBy() {
        List<Book> bookList = bookRepository.findByBookNameLike("游");
        log.info(bookList.toString());
    }

}
