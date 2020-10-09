<#list users>
{
    <#items as user>
    {
        "name": ${user.name},
        "age": ${user.age}
    }<#sep>, </#sep>
    </#items>
}
<#else>
{}
</#list>
