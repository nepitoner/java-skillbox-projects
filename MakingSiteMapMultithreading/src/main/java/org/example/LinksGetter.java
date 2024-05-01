package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;

public class LinksGetter extends RecursiveTask<Set<String>> {
    private final String url;
    private static final Set<String> linksSet = ConcurrentHashMap.newKeySet();
    LinksGetter (String url) {
        this.url = url;
    }
    @Override
    protected Set<String> compute() {
        List<LinksGetter> taskList = new ArrayList<>();
        Set<String> links = new TreeSet<>();

        try {
            Thread.sleep((50));
            Document doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
            Elements elements = doc.select("a");

            for(Element element : elements) {
                String href = element.absUrl("href").replaceAll("/$", "");
                if (!href.contains(url) || !linksSet.add(href)
                    || href.contains("#")) {
                    continue;
                }
                links.add(href);
                LinksGetter task = new LinksGetter(href);
                task.fork();
                taskList.add(task);
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }

        for (LinksGetter task : taskList) {
            links.addAll(task.join());
        }
        return links;
    }
}
