<#macro pagination p>
<#if (p.thisPageElements?size > 0)>
<div class="dataTables_paginate paging_bootstrap">
  <ul class="pagination">
    <#if !p.isFirstPage()><li><a class="pre mr-10" href="javascript:gotoPage(${p.pageNumber-1})"><i class="icon-double-angle-left"></i></a></li></#if>
    <#if p.pageNumber &gt;= (p.firstPageNumber + 5)>
      <li><a href="javascript:gotoPage(${p.pageNumber-5})">${p.pageNumber-5}</a></li>
    </#if>
    <#if p.pageNumber &gt;= (p.firstPageNumber + 4)>
      <li><a href="javascript:gotoPage(${p.pageNumber-4})">${p.pageNumber-4}</a></li>
    </#if>
    <#if p.pageNumber &gt;= (p.firstPageNumber + 3)>
      <li><a href="javascript:gotoPage(${p.pageNumber-3})">${p.pageNumber-3}</a></li>
    </#if>
    <#if p.pageNumber &gt;= (p.firstPageNumber + 2)>
      <li><a href="javascript:gotoPage(${p.pageNumber-2})">${p.pageNumber-2}</a></li>
    </#if>
    <#if p.pageNumber &gt;= (p.firstPageNumber + 1)>
      <li><a href="javascript:gotoPage(${p.pageNumber-1})">${p.pageNumber-1}</a></li>
    </#if>
    <#if p.pageNumber &gt;= p.firstPageNumber>
      <li class="active"><a href="javascript:gotoPage(${p.pageNumber})">${p.pageNumber}</a></li>
    </#if>
    <#if p.pageNumber &lt;= (p.lastPageNumber - 1)>
      <li><a href="javascript:gotoPage(${p.pageNumber+1})">${p.pageNumber+1}</a></li>
    </#if>
    <#if p.pageNumber &lt;= (p.lastPageNumber - 2)>
      <li><a href="javascript:gotoPage(${p.pageNumber+2})">${p.pageNumber+2}</a></li>
    </#if>
    <#if p.pageNumber &lt;= (p.lastPageNumber - 3)>
      <li><a href="javascript:gotoPage(${p.pageNumber+3})">${p.pageNumber+3}</a></li>
    </#if>
    <#if p.pageNumber &lt;= (p.lastPageNumber - 4)>
      <li><a href="javascript:gotoPage(${p.pageNumber+4})">${p.pageNumber+4}</a></li>
    </#if>
    <#if p.pageNumber &lt;= (p.lastPageNumber - 5)>
      <li><a href="javascript:gotoPage(${p.pageNumber+5})">${p.pageNumber+5}</a></li>
    </#if>
    <#if !p.isLastPage()><li><a class="next" href="javascript:gotoPage(${p.pageNumber+1})"><i class="icon-double-angle-right"></i></a></li></#if>
  </ul>
</div><!-- /.pagination -->
<script>
function gotoPage(page){
  $("input[name=pageNumber]").val(page);
  $("#myform").submit();
}
</script>
</#if>
</#macro>

<#macro paginationForGet>
  <ul class="pagination" style="float: right;margin-top: 0px;">
    <#if !p.isFirstPage()>
      <li>
        <a href="${action.getPaginationUrl(p.pageNumber-1)}">
          <i class="icon-double-angle-left"></i>
        </a>
      </li>
    </#if>
    <#if p.pageNumber == p.lastPageNumber>
      <#if p.lastPageNumber == 4>
        <li>
          <a href="${action.getPaginationUrl(p.pageNumber-3)}">${p.pageNumber-3}</a>
        </li>
      </#if>
      <#if p.lastPageNumber &gt; 4>
        <li>
          <a href="${action.getPaginationUrl(p.pageNumber-3)}">${p.pageNumber-3}</a>
        </li>
        <li>
          <a href="${action.getPaginationUrl(p.pageNumber-4)}">${p.pageNumber-4}</a>
        </li>
      </#if>
    </#if>
    <#if p.pageNumber == (p.lastPageNumber-1)>
      <#if p.lastPageNumber &gt; 4>
        <li>
          <a href="${action.getPaginationUrl(p.pageNumber-3)}">${p.pageNumber-3}</a>
        </li>
      </#if>
    </#if>
    <#if p.pageNumber &gt;= (p.firstPageNumber + 2)>
      <li>
        <a href="${action.getPaginationUrl(p.pageNumber-2)}">${p.pageNumber-2}</a>
      </li>
    </#if>
    <#if p.pageNumber &gt;= (p.firstPageNumber + 1)>
      <li>
        <a href="${action.getPaginationUrl(p.pageNumber-1)}">${p.pageNumber-1}</a>
      </li>
    </#if>
    <#if p.pageNumber &gt;= p.firstPageNumber>
      <li class="active">
        <a href="${action.getPaginationUrl(p.pageNumber)}">${p.pageNumber}</a>
      </li>
    </#if>
    <#if p.pageNumber &lt;= (p.lastPageNumber - 1)>
      <li>
        <a href="${action.getPaginationUrl(p.pageNumber+1)}">${p.pageNumber+1}</a>
      </li>
    </#if>
    <#if p.pageNumber &lt;= (p.lastPageNumber - 2)>
      <li>
        <a href="${action.getPaginationUrl(p.pageNumber+2)}">${p.pageNumber+2}</a>
      </li>
    </#if>
    <#if p.pageNumber == 1>
      <#if p.lastPageNumber == 4>
        <li>
          <a href="${action.getPaginationUrl(p.pageNumber+3)}">${p.pageNumber+3}</a>
        </li>
      </#if>
      <#if p.lastPageNumber &gt; 4>
        <li>
          <a href="${action.getPaginationUrl(p.pageNumber+3)}">${p.pageNumber+3}</a>
        </li>
        <li>
          <a href="${action.getPaginationUrl(p.pageNumber+4)}">${p.pageNumber+4}</a>
        </li>
      </#if>
    </#if>
    <#if p.pageNumber == 2>
      <#if p.lastPageNumber &gt; 4>
        <li>
          <a href="${action.getPaginationUrl(p.pageNumber+3)}">${p.pageNumber+3}</a>
        </li>
      </#if>
    </#if>
    <#if !p.isLastPage()>
      <li>
        <a href="${action.getPaginationUrl(p.pageNumber+1)}">
          <i class="icon-double-angle-right"></i>
        </a>
      </li>
    </#if>
  </ul>
</#macro>
